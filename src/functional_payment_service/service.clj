(ns functional-payment-service.service
  (:use [clojure.pprint])
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [schema.core :as s]
            [schema.coerce :as coerce]
            [functional-payment-service.controllers.payments :as controllers.payments]
            [functional-payment-service.schemata.payments :as schemata.payments]))

(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))
(defn home-page
  [request]
  (ring-resp/response "Hello World!"))

(def payment-request-input-parser
  (coerce/coercer schemata.payments/PaymentRequest coerce/json-coercion-matcher))

(defn create-payment-handler
  [{:keys [json-params] :as request}]
  (let [payment (payment-request-input-parser json-params)]
    {:status 200
     :body (controllers.payments/new-payment-attempt! payment)}))

;; TODO: finish this
(defn list-payment-handler
  [{:keys [path-params] :as request}]
  (pprint path-params)
  {:status 200
   :body (controllers.payments/list 1)})

(def common-interceptors [(body-params/body-params) http/html-body])

(def routes #{["/" :get (conj common-interceptors `home-page)]
              ["/about" :get (conj common-interceptors `about-page)]

              ["/api/customers/:id/payments" :post (conj common-interceptors `create-payment-handler)]
              ["/api/customers/:id/payments" :get (conj common-interceptors `list-payment-handler)]})

(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/type :jetty
              ::http/port 8080
              ::http/container-options {:h2c? true
                                        :h2? false
                                        :ssl? false}})
