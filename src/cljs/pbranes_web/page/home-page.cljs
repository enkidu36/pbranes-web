(ns pbranes-web.page.home-page
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce timer (reagent/atom (js/Date.)))

(defonce time-color (reagent/atom "#f34"))

(defonce elapse-color (reagent/atom "#f00"))

(defonce colors ["#f00" "#0f0" "#00f"])

(defonce color-index (reagent/atom 0))

(defonce time-updater (js/setInterval
                         #(reset! timer (js/Date.)) 1000))

(defn greeting [message]
  [:h1 message])

(defn clock []
  (let [time-str (-> @timer .toTimeString (clojure.string/split " ") first)]
    [:div.example-clock
     {:style {:color @time-color
              :font-size 150}}
     time-str]))

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change #(reset! time-color (-> % .-target .-value))}]])

(defn simple-example []
  [:div
   [greeting "Hello, world! The time is."]
   [clock]
   [color-input]])

(defn home-page []
  [:div [simple-example]])

