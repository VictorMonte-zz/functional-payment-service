(ns functional-payment-service.models.payment-attempt
  (:require [schema.core :as s]))

(s/defschema PaymentAttempt
  #:payment-attempt {:id s/Uuid
                     :amount s/Int
                     :product-id s/Str})
