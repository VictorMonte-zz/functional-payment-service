(ns functional-payment-service.utils.uuid)

(defn uuid []
  (.toString (java.util.UUID/randomUUID)))
