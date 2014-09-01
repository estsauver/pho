(ns pho.core
  (:require [org.httpkit.client :as http]
            [clojure.pprint :refer :all]
            [clojure.data.json :as json]
            [clojure.set :refer :all]))
@(http/get "http://www.google.com")


(defn etcd-uri []
  "http://localhost:4001/v2/keys")

(defn etcd-response [waitIndex] (json/read-str (:body @(http/get (str (etcd-uri) "/services/")
                              {:query-params {:wait "true"
                                              :recursive "true"
                                              :waitIndex waitIndex}
                               :timeout 30000}))))


(defn get-ip [response]
  (clojure.string/replace (get-in response ["node" "key"]) #"/services/" ""))


(defn tokenize-roles [rawstring]
  (set (filter #(not (clojure.string/blank? %))
               (clojure.string/split (or rawstring
                                           "")
                                     #","))) )
(defn old-values [response]
  (tokenize-roles (get-in response ["prevNode" "value"])))


(defn new-values [response]
  (tokenize-roles (get-in response ["node" "value"])))


(defn added-values [response]
  (let [new (new-values response)
        old (old-values response)]
    (difference new old)
    ))



(defn removed-values [response]
  (let [new (new-values response)
        old (old-values response)]
    (difference old new)
    ))

(defn process-changes [key-changes]
  ) D
