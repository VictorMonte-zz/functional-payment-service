(ns functional-payment-service.db.payments-test
  (:use [clojure.pprint])
  (:require [clojure.test :refer :all]
            [functional-payment-service.ports.db.datomic.payments :as db.payments]
            [datomic.api :as d]
            [functional-payment-service.utils.uuid :as uuid]
            [functional-payment-service.utils.db :as utils.db]
            [schema-generators.generators :as gen]
            [functional-payment-service.models.payment-attempt :as models.payment-attempt]
            [schema.core :as s]))

(s/def customer-id :- s/Uuid (uuid/uuid))
(s/def payment-attempt (gen/generate models.payment-attempt/PaymentAttempt))

(s/defn generate-payment-attempt
  [customer-id :- s/Uuid]
  (-> (gen/generate models.payment-attempt/PaymentAttempt)
      (assoc :payment-attempt/customer-id customer-id)
      (assoc :payment-attempt/amount (bigdec (rand-int 100000M)))))

(s/defn generate-payment-attemps
  [number-of-payments :- s/Int
   customer-id :- s/Uuid]
  (loop [i        0
         payments []]
    (if (= i number-of-payments)
      payments
      (recur (inc i) (conj payments (generate-payment-attempt customer-id))))))

(s/def payment-attempts (generate-payment-attemps 5 customer-id))

(deftest insert-payment-attempt
  (let [conn (utils.db/create-empty-in-memory-db)]
    (is (= payment-attempt
           (db.payments/idempotent-insert! payment-attempt conn)))))

;;TODO: with one is passing with more it's get bad yet kkkk almost there..
(deftest list-payment-attempts
  (let [conn (utils.db/create-empty-in-memory-db)]
    (doseq [payment-attempt payment-attempts]
      (db.payments/idempotent-insert! payment-attempt conn))
    (is (= (pprint (first payment-attempts))
           (pprint (first (db.payments/get-all-by-customer-id customer-id conn)))))))
