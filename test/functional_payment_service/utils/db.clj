(ns functional-payment-service.utils.db
  (:require [datomic.api :as d]))

(defn create-empty-in-memory-db []
  (let [uri "datomic:mem://payments-test-db"]
    (d/delete-database uri)
    (d/create-database uri)
    (let [conn (d/connect uri)
          schema (load-file "resources/schema.edn")]
      (d/transact conn schema)
      conn)))
