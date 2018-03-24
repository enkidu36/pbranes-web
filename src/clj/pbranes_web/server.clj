(ns pbranes-web.server
  (:require [pbranes-web.handler :refer [app]]
            [config.core :refer [env]]
            [clojure.tools.logging :as log]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(log/info "hello")

(defn -main [& args]
   (let [port (Integer/parseInt (or (env :port) "3000"))]
     (run-jetty app {:port port :join? false})))
