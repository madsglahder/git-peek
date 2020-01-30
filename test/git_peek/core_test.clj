(ns git-peek.core-test
  (:require [clojure.test :refer :all]
            [git-peek.core :refer :all]
            [cheshire.core :as json]))

(deftest test-http-request->map
  (testing "Testing http-request->map"
    (assert (= (http-request->map {:body (json/generate-string {:a "a"})})
               {:a "a"}))))

(deftest test-find-commit-for-year
  (testing "Testing find-commit-for-year"
    (assert (= (find-commit-for-year {:body (json/generate-string {:created_at "2010105195619061409563405"})}
                                     2010)))))

(deftest test-take-body-author-username-from-single-comment
  (testing "Testing take-body-author-username-from-single-comment"
    (assert (= (take-body-author-username-from-single-comment {:body "text"
                                                               :user {:login "username"}
                                                               :created_at "2010"})
               {:body "text", :user "username", :date "2010"}))))

(deftest test-take-commit-items-for-year
  (testing "Testing take-commit-items-for-year"
    (assert (= (take-commit-items-for-year (repeat 3
                                                   {:body (json/generate-string {:created_at "2010105195619061409563405"})})
                                           2010)))))

(deftest test-find-programming-languages-in-request
  (testing "Testing find-programming-languages-in-request"
    (assert (= (name
                 (first
                   (find-programming-languages-in-request {:body "{\"PLang\":69420}"})))
               (str "PLang")))))
