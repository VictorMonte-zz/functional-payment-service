(ns functional-payment-service.utils.uuid)

(defn uuid []
  (java.util.UUID/randomUUID))
