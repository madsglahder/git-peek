(ns git-peek.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.edn :as edn])
  (:import java.util.Date
           (java.text SimpleDateFormat))
  (:gen-class))

(def date-parse-structure (SimpleDateFormat. "yyyy"))

(defn http-request->map
  "Takes a returned request and finds, then parses the body
   of the text from serialized json to a map"
  [http-request]
  (json/parse-string (:body http-request)
                     true))

(defn find-programming-languages-in-request
  "In a request finds the programming languages
   and returns them as a sequence"
  [request]
  (map name (keys (http-request->map request))))

(defn is-comment-from-correct-year?
  "Checks whether comment is from given year.
   Returns true / fails"
  [comment-map year]
  (if (= (:created_at comment-map) year)
    true
    false))

(defn find-body-author-commit-for-year
  "In a request for a given year finds body, authors username
   and date for all commit comments for a given year"
  [request year]
  (let [request (http-request->map request)]
    (map)))


(def rando-content (http-request->map (client/get "https://api.github.com/repos/Shopify/shopify_api/comments")))
