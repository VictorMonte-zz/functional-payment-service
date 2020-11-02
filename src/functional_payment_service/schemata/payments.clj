(ns functional-payment-service.schemata.payments
  (:require [schema.core :as s]))

(s/defschema PaymentRequest
  {:amount s/Int
   :product-id s/Int})
