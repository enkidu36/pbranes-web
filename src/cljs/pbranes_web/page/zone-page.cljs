(ns pbranes-web.page.zone-page
  (:require [reagent.core :as reagent :refer [atom]]
            [pbranes-web.zones :as zones]
            [ajax.core :refer [GET POST]]))

(defonce ftp (reagent/atom 0))

(defn ftp-input []
  (let [ x (.-value (js/document.getElementById "test-value"))]
    (if (nil? x) 0  x)))

(defn input-comp []
  [:div#ftp-value
   [:div.form-group.col-sm-6
    [:label.control-label {:for "test-value"} "FTP value: "]
      [:input#test-value.form-control.input-sm {:type "text"
                                                :placeholder "Enter FTP test value"
                                                :on-blur #(reset! ftp (ftp-input))
                                                :on-keyPress #(when (=  (.-charCode  %) 13)
                                                                  (reset! ftp (ftp-input)))}]]])

(defn table [input]
  [:table.table.table-striped.table-bordered {:style {:width 1000 :margin-left 20}}
    [:thead
     [:tr
      [:th "Current FTP"]
      [:th {:colSpan "3"} "Watts"]]
     [:tr
      [:th "Zone-Purpose"]
      [:th "Lower"]
      [:th "Upper"]
      [:th "% of FTP"]]]
    [:tbody
      (for [row input
            :let [[name upper lower range] row]]
      ^{:key name}
        [:tr
         [:td name]
         [:td upper]
         [:td lower]
         [:td range]])]])

(defn get-ftp []
  (GET "/ftp"
        {:handler #(.log js/console (str "response:" %))
         :response-format :json}))

(defn zone-page []
  [:div.row
   [input-comp]
   [table (zones/calc-power zones/coggin @ftp)]])



