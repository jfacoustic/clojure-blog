(ns clojure-blog.core
  (:require [ring.adapter.jetty :as webserver]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]])
  (:gen-class))

(defn wrap-logger
  [handler file]
  (fn [request]
    (do
      (let [body (:body request)]
        (spit file (str body "\n") :append true)
        (handler request)))))

(defn welcome
  [request]
  {:status 200
   :body "<h1>Learning ring and compojure now.</h1>"
   :headers {}})

(defn write-file
  [title content]
  (spit (str title ".txt") content))

(defn create-story
  [request]
  (do
    (write-file (:title (:body request))
                (:content (:body request)))
    (response (:title (:body request)))))

(defroutes app
  (GET "/" [] welcome)
  (POST "/story" [] create-story)
  (not-found "<h1>Unable to find this page.</h1>"))

(defn -dev-main
  "Just getting started..."
  [& args]
  (let [port-number (or (first args) 3000)]
    (webserver/run-jetty
     (-> #'app
         (wrap-logger "log.txt")
         (wrap-json-body {:keywords? true})
         (wrap-json-response)
         (wrap-reload))
     {:port (Integer. port-number)
      :join? false})))

(defn -main
  "Just getting started..."
  [& args]
  (let [port-number (or (first args) 3000)]
    (webserver/run-jetty
     (-> #'app
         (wrap-logger "log.txt")
         (wrap-json-body {:keywords? true})
         (wrap-json-response))
     {:port (Integer. port-number)})))
