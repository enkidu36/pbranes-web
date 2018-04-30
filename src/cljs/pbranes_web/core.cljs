(ns pbranes-web.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [pbranes-web.page.dash-page :as d]
              [pbranes-web.page.zone-page :as z]
              [pbranes-web.page.about-page :as a]))

;;-----------------
;; View
(defonce page (atom #'z/zone-page))

(defn menu []
  [:ul.list-inline
   [:li.active [:a {:href "/"} "Home"]]
   [:li [:a {:href "/dash"} "Dashboard"]]
   [:li [:a {:href "/about"} "About"]]])

(defn header []
  [:div#jumbo.jumbotron
   [:div.row
    [:font.col-sm-6.footer {:size "24"} "PBrane's Training" ]
    [:img#header-img.col-sm-6 {:src "/img/formula.png"} ]]
     [menu]])

(defn footer []
  [:div.well.footer {:id "footer"} "footer"])

(defn current-page []
  [:div.container
    [header]
    [:div.page [@page]]
    [footer]])

;; -------------------------
;; Routes
(secretary/defroute "/dash" []
  (reset! page #'d/dashboard-page))

(secretary/defroute "/zone" []
  (reset! page #'z/zone-page))

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
