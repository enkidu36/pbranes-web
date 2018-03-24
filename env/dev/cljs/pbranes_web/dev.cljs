(ns ^:figwheel-no-load pbranes-web.dev
  (:require
    [pbranes-web.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
