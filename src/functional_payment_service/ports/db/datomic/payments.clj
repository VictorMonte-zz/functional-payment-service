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
            '[:payment-attempt/id :payment-attempt/amount :payment-attempt/product-id]
            temp-id)))
