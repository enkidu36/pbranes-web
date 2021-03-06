(ns pbranes-web.server
  (:require [pbranes-web.handler :refer [app]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))
;; hello
(defn -main [& args]
   (let [port (Integer/parseInt (or (get (System/getenv) "PORT") "3000"))]
     (run-jetty app {:port port :join? false})))
