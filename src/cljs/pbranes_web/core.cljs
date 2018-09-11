(ns pbranes-web.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [pbranes-web.page.dash-page :as d]
              [pbranes-web.page.power-page :as p]
              [pbranes-web.page.heartrate-page :as h]
              [pbranes-web.page.about-page :as a]))

;;-----------------
;; View
(defonce page (atom #'p/render-power-page))

(defn menu []
  [:ul.list-inline
   [:li.active [:a {:href "/"} "Power"]]
   [:li [:a {:href "/heartrate"} "Heart Rate"]]
   [:li [:a {:href "/dash"} "Dashboard"]]
   [:li [:a {:href "/about"} "About"]]])

(defn header []
  [:div#jumbo.jumbotron
   [:div.row
    [:font.col-sm-6.footer {:size "24"} "PBrane's Training" ]]
     [menu]])

(defn footer []
  [:div.well.footer {:id "footer"} "footer"])

(defn current-page []
  [:div.container
   [:div.page
    [header]
    [:div [@page]]
    [footer]]])

;; -------------------------
;; Routes
(secretary/defroute "/" []
  (reset! page #'p/render-power-page))

(secretary/defroute "/heartrate" []
  (reset! page #'h/render-heartrate-page))

(secretary/defroute "/dash" []
  (reset! page #'d/dashboard-page))

(secretary/defroute "/about" []
  (reset! page #'a/about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render
    [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
