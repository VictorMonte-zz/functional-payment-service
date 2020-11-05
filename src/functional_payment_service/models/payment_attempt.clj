(ns functional-payment-service.models.payment-attempt
  (:require [schema.core :as s]
            [functional-payment-service.utils.schema :as u-schema]))

(s/defschema PaymentAttempt
  #:payment-attempt {:id s/Uuid
                     :amount s/Int
                     :product-id s/Uuid
                     :customer-id s/Uuid})
