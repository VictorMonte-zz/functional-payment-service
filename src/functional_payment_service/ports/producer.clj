(ns functional-payment-service.ports.producer
  (:require [schema.core :as s]
            [functional-payment-service.models.payment-attempt :as models.payment-attempt]))

;; TODO: produce message to kafka
(s/defn payment-attempt!
  [payment-attempt :- models.payment-attempt/PaymentAttempt]
  nil)
