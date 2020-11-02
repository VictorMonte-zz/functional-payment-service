(ns functional-payment-service.db.config
  (:require [datomic.api :as d]))

;; TODO: set url in some config file
(def uri "datomic:dev://localhost:4334/hello")

(def schema-tx (read-string (slurp "resources/schema.edn")))
;; (def data-tx (read-string (slurp "resources/seed-data.edn")))

(defn init-db []
  (when (d/create-database uri)
    (let [conn (d/connect uri)]
      @(d/transact conn schema-tx)
      #_@(d/transact conn data-tx))))
