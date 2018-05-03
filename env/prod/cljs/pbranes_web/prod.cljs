(ns pbranes-web.prod
  (:require [pbranes-web.core :as core]))

;;ignore println statements in prod env
(set! *print-fn* (fn [& _]))

(core/init!)
