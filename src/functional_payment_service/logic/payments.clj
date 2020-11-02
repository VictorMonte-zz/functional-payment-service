(ns functional-payment-service.logic.payments
  (:require [functional-payment-service.schemata.payments :as schemata.payments]
            [functional-payment-service.models.payment-attempt :as models.payment-attempt]
            [schema.core :as s]))

(s/defn payment-attempt :- models.payment-attempt/PaymentAttempt
  [payment-request :- schemata.payments/PaymentRequest]
  #:payment-attempt{:id         (.toString (java.util.UUID/randomUUID))
                    :amount     (:amount payment-request)
                    :product-id (:producet-id payment-request)})
