(ns pbranes-web.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [pbranes-web.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]))

;; Too many environment variables to get all
(def env-select-keys [:dev :database-url :ftp])

(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run"
       [:b "lein figwheel"]
       " in order to start the compiler"]])

(defn head []
  [:head
   [:title "Pbranes"]
   [:link {:rel "shortcut icon" :type "image/x-icon" :href "/img/favicon.ico"}]
   [:link {:rel "icon" :href "/img/favicon.ico" :type "image/x-icon"}]
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css"))
   (include-css "/css/bootstrap-theme.css")
   (include-css "/css/bootstrap.css")])

(defn loading-page []
  (html5
    (head)
    [:body.body-container
     mount-target
     (include-js "/js/app.js")
     (include-js "/js/bootstrap.js")] ))

(defn config-report []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body  (html5
            (head)
            [:body (map #(str "<b>"  (name %) ":</b>&nbsp;&nbsp;&nbsp;" (% env) "<br/>") env-select-keys)])})

(defroutes routes
           (GET "/" [] (loading-page))
           (GET "/about" [] (loading-page))
           (GET "/config" [] (config-report))
           (resources "/")
           (not-found "Not Found"))

(def app (wrap-middleware #'routes))
