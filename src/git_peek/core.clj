(ns git-peek.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:gen-class))

(defn http-request->map
  "Takes a returned request and finds, then parses the body
   of the text from serialized json to a map"
  [http-request]
  (json/parse-string (:body http-request)
                     true))

(defn find-commit-for-year
  "In a request for a given year finds body, authors username
   and date for all commit comments for a given year"
  [request-from-rest year]
  (let [requests (http-request->map request-from-rest)]
    (filter #(= (str year)
                (subs (% :created_at) 0 4))
            requests)))

(defn take-body-author-username-from-single-comment
  "From a map of a commit extracts the commit body,
   the username and the creation date for the commit
   comment"
  [filtered-request]
  {:body (:body filtered-request)
   :user (get-in filtered-request [:user :login])
   :date (:created_at filtered-request)})

(defn take-commit-items-for-year
  "Takes author, text body and commit date for
   a given year and returns them as a sequence of maps"
  [request-from-rest year]
  (map take-body-author-username-from-single-comment
       (find-commit-for-year request-from-rest year)))

(defn find-programming-languages-in-request
  "In a request finds the programming languages
   and returns them as a sequence
   Feed in /:repo/languages and get a sequence
   of the programming languages used"
  [request]
  (keys (json/parse-string (:body request))))

(defn get-repo-info
  "Takes an organisation name, repo name and request type
   and returns a call from the github api fort the
   given place
   Request type can be either comments or languages"
  [org-name repo-name request-type]
  (client/get (str "https://api.github.com/repos/"
                   org-name
                   "/"
                   repo-name
                   "/"
                   request-type)))

(defn helper-get-repo-listing
  "Takes an organisation name and returns their repo
   listing sorted by newness for most recent update"
  [org-name number]
  (take number (http-request->map(client/get(str "https://api.github.com/orgs/"
                                                  org-name
                                                  "/repos")))))

(defn helper-programming-languages
  "Helper function for getting programming languages
   but only from shopify repos"
  [repo]
  (find-programming-languages-in-request
    (get-repo-info "Shopify"
                   repo
                   "languages")))

(defn helper-commits-year
  "Helper function for finding commits by year for repo"
  [year repo]
  (take-commit-items-for-year (get-repo-info "Shopify"
                                             repo
                                             "comments")
                              year))
