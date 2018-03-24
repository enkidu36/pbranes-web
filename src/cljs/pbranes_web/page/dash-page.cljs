(ns pbranes-web.page.dash-page)

(defn  recovery []
  [:li#recovery  "1x10min 140watts Recovery"])

(defn dashboard-page []
  [:div
   [:h2 "Kitchen Sink"]
   [:p "30min Zone 2"]
   [:p "Repeat 3 times"]
     [:ul
      [:li "10x1min (280 - 320watts)"]
      [recovery]
      [:li "1x20min (260watts)"]
      [recovery]
      [:li "10x30sec Hill Stomps 50rpm"]
      [recovery]]])
