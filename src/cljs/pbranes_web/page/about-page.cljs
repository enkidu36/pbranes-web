(ns pbranes-web.page.about-page
    (:require [reagent.core :as reagent]))

(defn about-page []
  [:div [:h2 "About myreagent"]
   [:div [:a {:href "/"} "go to the home page"]]])

