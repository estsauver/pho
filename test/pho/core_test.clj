(ns pho.core-test
  (:require [clojure.test :refer :all]
            [pho.core :refer :all]))

(deftest test-no-added-value
  (testing "Adding a value"
    (let [response1 {"node" {"value" "mysql"} "prevNode" {"value" "mysql,webapp"}}]
      (is (= #{} (added-values response1))))))

(deftest test-adding-one-value
  (testing "Adding a value"
    (let [response1 {"node" {"value" "redis,mysql"} "prevNode" {"value" "mysql,webapp"}}]
      (is (= #{"redis"} (added-values response1))))))

(deftest test-adding-multiple-values
  (testing "Adding a value"
    (let [response1 {"node" {"value" "postgres,mysql,etcd"} "prevNode" {"value" "mysql,webapp"}}]
      (is (= #{"postgres" "etcd"} (added-values response1))))))

(deftest test-get-ip
  (testing "Getting IP from response"
    (let [response {"node" {"key" "/services/192.168.0.1"}}]
      (is (= "192.168.0.1" (get-ip response))))))

(deftest test-no-removed-value
  (testing "Adding a value"
    (let [response1 {"node" {"value" "mysql,webapp"} "prevNode" {"value" "mysql,webapp"}}]
      (is (= #{} (removed-values response1))))))

(deftest test-one-removed-value
  (testing "Adding a value"
    (let [response1 {"node" {"value" "webapp,redis"} "prevNode" {"value" "redis,mysql,webapp"}}]
      (is (= #{"mysql"} (removed-values response1))))))

(deftest test-multiple-removed-values
  (testing "Adding a value"
    (let [response1 {"node" {"value" "redis"} "prevNode" {"value" "redis,mysql,webapp"}}]
      (is (= #{"mysql" "webapp"} (removed-values response1))))))

