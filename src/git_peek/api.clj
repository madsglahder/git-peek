(ns git-peek.api
  (:require [compojure.core :as ccore]
            [compojure.route :as route]
            [org.httpkit.server :as server]
            [ring.middleware.defaults :as ring]
            [git-peek.core :as gp]
            [cheshire.core :as json])
  (:gen-class))

(def status-headers {:status 200
                     :headers {"Content-Type" "text/html"}})

(defn repo-languages
  [req]
  (assoc status-headers :body (json/generate-string
                                (gp/helper-programming-languages
                                  (:repo (:params req))))))

(defn repo-comments
  [repo year]
  (println repo year)
  (assoc status-headers :body (json/generate-string
                                (gp/helper-commits-year
                                  repo
                                  year))))

(defn org-repo-recents
  [orgname amount]
  (assoc status-headers :body (json/generate-string
                                (gp/helper-get-repo-listing
                                  orgname
                                  amount))))

(ccore/defroutes app-routes
                 (ccore/GET "/" [] "Hello World, I'm alive")
                 (ccore/GET "/shopify/repo-languages" [] repo-languages) ;; ok
                 (ccore/GET "/shopify/repo-comments"
                            [repo year] (repo-comments repo year))
                 (ccore/GET "/shopify/org-repo-recents"
                            [orgname amount] (org-repo-recents orgname amount))
                 (route/not-found "404 not found"))

(defn -main
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (server/run-server (ring/wrap-defaults
                         #'app-routes
                         ring/site-defaults)
                       {:port port})
    (println (str "Running webserver at localhost:" port "/"))))
