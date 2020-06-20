(ns clojure-blog.core
  (:require [ring.adapter.jetty :as webserver]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]])
  (:gen-class))

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
  [& args]
  (let [port-number (or (first args) 3000)]
    (webserver/run-jetty
     (wrap-reload #'app)
     {:port (Integer. port-number)
      :join? false})))

(defn -main
  "Just getting started..."
  [& args]
  (let [port-number (or (first args) 3000)]
    (webserver/run-jetty
     app
     {:port (Integer. port-number)})))
