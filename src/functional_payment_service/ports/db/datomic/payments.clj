(ns functional-payment-service.ports.db.datomic.payments
  (:use [clojure.pprint])
  (:require [schema.core :as s]
            [functional-payment-service.models.payment-attempt :as models.payment-attempt]
            [datomic.api :as d]))

;; TODO: use async
;; TODO: extract conn to component
;; TODO: schema conn
(s/defn idempotent-insert! :- models.payment-attempt/PaymentAttempt
  [payment-attempt :- models.payment-attempt/PaymentAttempt
   conn]
  (let [result   @(d/transact conn [payment-attempt])
        db-after (:db-after result)
        temp-id  (-> result :tempids vals first)]
    (d/pull db-after
            '[:payment-attempt/id :payment-attempt/amount :payment-attempt/product-id :payment-attempt/customer-id]
            temp-id)))

(s/defn get-all-by-customer-id :- [models.payment-attempt/PaymentAttempt]
  [customer-id :- s/Uuid
   conn]
  (let [db (d/db conn)]
    (d/q '[:find ?id ?amount ?product-id ?customer-id
           :in $ ?customer-id
           :keys payment-attempt/id payment-attempt/amount payment-attempt/product-id payment-attempt/customer-id
           :where
           [?e :payment-attempt/id ?id]
           [?e :payment-attempt/product-id ?product-id]
           [?e :payment-attempt/amount ?amount]
           [?e :payment-attempt/customer-id ?customer-id]]
         db customer-id)))
