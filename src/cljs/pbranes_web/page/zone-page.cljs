(ns pbranes-web.page.zone-page
  (:require [reagent.core :as reagent :refer [atom]]
            [pbranes-web.zones :as zones]
            [clojure.string :as s]))

(defonce ftp-state (reagent/atom {:ftp 0 :method zones/coggin}) )

(defn get-ftp-value [e]
  (let [ x (-> e .-target .-value)]
    (if (nil? x) 0  x)))

(defn get-method-value [e]
  (let [x (-> e .-target .-value)]
    (cond
      (= x ":friel") zones/friel
      (= x ":joe") zones/joe
      :default zones/coggin)))

(defn swap-value!
  [param value]
  (swap! ftp-state assoc param value))

(defn ftp-component []
  [:div#ftp-value.col-sm-3
   [:div.form-group
    [:label.control-label {:for "test-value"} "FTP value: "]
    [:input#test-value.form-control.input-sm-1 {:type "text"
                                              :placeholder "Enter FTP test value"
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
        prefix "Current FTP"]
    (if (or (s/blank? val) (zero? (int val)))
      prefix
      (str prefix " ( " val " watts   )" ))))

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
        [:th.table-center  "Percentage of FTP"]]]
      [:tbody
        (for [row input
              :let [[name upper lower range] row]]
        ^{:key name}
          [:tr
           [:td name]
           [:td upper]
           [:td lower]
           [:td range]])]]]])

(defn zone-page []
   [:div
     [ftp-component]
     [table (zones/calc-power (:zones (:method @ftp-state)) (:ftp @ftp-state))]])



