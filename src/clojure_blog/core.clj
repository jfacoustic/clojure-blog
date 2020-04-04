(ns clojure-blog.core
  (:require [ring.adapter.jetty :as webserver]))

(defn -main
  "Just getting started..."
  [port-number]
  (webserver/run-jetty
                       (fn [request]
                         {:status 200
                          :headers {}
                          :body "<h1> Yo, does this work? </h1>"
                          })
                       {:port (Integer. port-number)
                        :join? false}))
