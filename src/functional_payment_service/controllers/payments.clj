(ns functional-payment-service.controllers.payments
  (:use [clojure.pprint])
  (:require [schema.core :as s]
            [functional-payment-service.logic.payments :as logic.payments]
            [functional-payment-service.ports.db.datomic.payments :as db.payments]
            [functional-payment-service.ports.producer :as ports.producer]
            [functional-payment-service.schemata.payments :as schemata.payments]))

(s/defn new-payment-attempt!
  [payment-request :- schemata.payments/PaymentRequest]
  (let [payment-attempt (logic.payments/payment-attempt payment-request)]
    (db.payments/idempotent-insert! payment-attempt)
    #_(ports.producer/payment-attempt! payment-attempt)))

(defn list
  [customer-id]
  (print "List payments"))
