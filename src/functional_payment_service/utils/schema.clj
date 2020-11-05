(ns functional-payment-service.utils.schema
  (:require [schema.core :as s]))

(def PositiveAmount
  "Only positive amounts, bigger or equals to 0"
  (s/pred #(>= % 0M)))
