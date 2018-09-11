(ns pbranes-web.page.heartrate-page
  (:require [reagent.core :as reagent :refer [atom]]
            [pbranes-web.zones :as z]
            [pbranes-web.calculator.power :as p]
            [clojure.string :as s]))

(defonce ftp-state (reagent/atom {:ftp 0 :method z/coggin}) )

(defn get-ftp-value [e]
  (let [ x (-> e .-target .-value)]
    (if (nil? x) 0  x)))

(defn get-method-value [e]
  (let [x (-> e .-target .-value)]
    (cond
      (= x ":friel") z/friel
      (= x ":joe") z/joe
      :default z/coggin)))

(defn swap-value!
  [param value]
  (swap! ftp-state assoc param value))

(defn ftp-component []
  [:div#ftp-value.col-sm-3
   [:div.form-group
    [:label.control-label {:for "test-value"} "LTHR value: "]
    [:input#test-value.form-control.input-sm-1 {:type "text"
                                              :placeholder "30 Min Avg HR"
                                              :on-blur #(swap-value! :ftp (get-ftp-value %))
                                              :on-keyPress #(when (=  (.-charCode  %) 13)
                                                                (swap-value! :ftp (get-ftp-value %)))}]]
  [:div.form-group
   [:label.control-label {:for "method-value"} "Method Selection:"]
   [:select.form-control.col-sm-3 {
                                   :on-change #(swap-value! :method (get-method-value %))}
    [:option {:value ":coggin" :active true} "Coggin Method"]
    [:option {:value ":friel"} "Friel Method"]
    [:option {:value ":joe"} "Joe's Method"]]]])

(defn method-name-text []
  (str (:method-name  (:method @ftp-state)) " method"))

(defn ftp-text []
  (let [val (:ftp @ftp-state)
        prefix "Entered LTHR"]
    (if (or (s/blank? val) (zero? (int val)))
      prefix
      (str prefix " ( " val " bpm   )" ))))

(defn table [input]
  [:div.row
   [:div.col-sm-8
    [:table.table.table-striped.table-bordered.table-center
      [:thead.footer
       [:tr
        [:th (ftp-text) ]
        [:th {:colSpan "3"} (method-name-text)]]
       [:tr
        [:th.table-center "Zone-Purpose"]
        [:th.table-center "Lower"]
        [:th.table-center "Upper"]
        [:th.table-center  "Percentage of LTHR"]]]
      [:tbody
        (for [row input
              :let [[name upper lower range] row]]
        ^{:key name}
          [:tr
           [:td name]
           [:td upper]
           [:td lower]
           [:td range]])]]]])

(defn render-heartrate-page []
   [:div
     [ftp-component]
     [table (p/calculate (:zones (:method @ftp-state)) (:ftp @ftp-state) "heartrate")]])



