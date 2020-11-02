(ns functional-payment-service.db.payments-test
  (:require [clojure.test :refer :all]
            [functional-payment-service.ports.db.datomic.payments :as db.payments]
            [datomic.api :as d]
            [functional-payment-service.utils.uuid :as uuid]
            [functional-payment-service.utils.db :as utils.db]
            [schema-generators.generators :as gen]
            [functional-payment-service.models.payment-attempt :as models.payment-attempt]
            [schema.core :as s]))

(def payment-attempt-id (uuid/uuid))
(def product-id (uuid/uuid))
(s/def payment-attempt
  #:payment-attempt{:id         payment-attempt-id
                    :amount     1000.0M
                    :product-id product-id})

;; TODO: use generators, but before allow only positive amounts
(deftest insert-payment-attempt
  (let [conn (utils.db/create-empty-in-memory-db)]
        (is (= payment-attempt
               (db.payments/idempotent-insert! payment-attempt conn)))))
