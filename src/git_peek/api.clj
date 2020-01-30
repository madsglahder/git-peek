(ns git-peek.api
  (:require [compojure.core :as ccore]
            [compojure.route :as route]
            [org.httpkit.server :as server]
            [ring.middleware.defaults :as ring]
            [git-peek.core :as gp]
            [cheshire.core :as json]))

(def status-headers {:status 200
                     :headers {"Content-Type" "text/html"}})

(defn repo-languages
  [req]
  (assoc status-headers :body (json/generate-string
                                (gp/helper-programming-languages
                                  (:repo (:params req))))))

(defn repo-comments
  [req]
  (assoc status-headers :body (json/generate-string
                                (gp/helper-commits-year
                                  (:repo (:params req))
                                  (:year (:params req))))))

(defn org-repo-recents
  [req]
  (assoc status-headers :body (json/generate-string
                                (gp/helper-get-repo-listing
                                  (:orgname (:params req))
                                  (:amount (:params req))))))

(ccore/defroutes app-routes
                 (ccore/GET "/" [] "Hello World, I'm alive")
                 (ccore/GET "/shopify/repo-languages" [] repo-languages)
                 (ccore/GET "/shopify/repo-comments" [] repo-comments)
                 (ccore/GET "/shopify/org-repo-recents" [] org-repo-recents)
                 (route/not-found "404 not found"))

(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (server/run-server (ring/wrap-defaults
                         #'app-routes
                         ring/site-defaults)
                       {:port port})
    (println (str "Running webserver at localhost:" port "/"))))
