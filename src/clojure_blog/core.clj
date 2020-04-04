(ns clojure-blog.core
  (:require [ring.adapter.jetty :as webserver]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]))

(defn welcome
  [request]
    {:status 200
     :body "<h1>Learning ring and compojre  now.</h1>"
     :headers {}})

(defroutes app
  (GET "/" [] welcome)
  (not-found "<h1>Unable to find this page.</h1>"))

(defn -dev-main
  "Just getting started..."
  [port-number]
  (webserver/run-jetty
                       (wrap-reload #'app)
                       {:port (Integer. port-number)
                        :join? false}))

(defn -main
  "Just getting started..."
  [port-number]
  (webserver/run-jetty
                      app
                       {:port (Integer. port-number)}))
