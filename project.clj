(defproject pbranes-web "0.1.0-SNAPSHOT"
  :description "Joe's Training Site"
  :url "http://localhost:3449/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring-server "0.5.0"]
                 [reagent "0.8.0"]
                 [reagent-utils "0.3.1"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [cheshire "5.8.0"]
                 [yogthos/config "1.1.1"]
                 [org.clojure/clojurescript "1.10.238" :scope "provided"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.4" :exclusions [org.clojure/tools.reader]]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [org.clojure/tools.logging "0.4.0"]]

  :plugins [[lein-environ "1.1.0"]
            [lein-cljsbuild "1.1.7"]
            [lein-asset-minifier "0.2.7"
             :exclusions [org.clojure/clojure]]]
  :ring {:handler pbranes-web.handler/app
         :uberwar-name "pbranes-web.war"}

  :min-lein-version "2.5.0"
  :uberjar-name "pbranes-web.jar"
  :main pbranes-web.server
  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]

  :minify-assets
  {:assets
   {"resources/public/css/site.min.css" "resources/public/css/site.css"}}

  :cljsbuild
  {:builds {:min
            {:source-paths ["src/cljs" "src/cljc" "env/prod/cljs"]
             :compiler
             {:output-to        "target/cljsbuild/public/js/app.js"
              :output-dir       "target/cljsbuild/public/js"
              :source-map       "target/cljsbuild/public/js/app.js.map"
              :optimizations :advanced
              :pretty-print  false}}
            :app
            {:source-paths ["src/cljs" "src/cljc" "env/dev/cljs"]
             :figwheel {:on-jsload "pbranes-web.core/mount-root"}
             :compiler
             {:main "pbranes-web.dev"
              :asset-path "/js/out"
              :output-to "target/cljsbuild/public/js/app.js"
              :output-dir "target/cljsbuild/public/js/out"
              :source-map true
              :optimizations :none
              :pretty-print  true}}
            }
   }


  :figwheel
  {:http-server-root "public"
   :server-port 3449
   :nrepl-port 7002
   :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"
                      ]
   :css-dirs ["resources/public/css"]
   :ring-handler pbranes-web.handler/app}



  :profiles {
              :dev [:project/repl :project/dev :profiles/dev]
              :project/repl {:repl-options {:init-ns pbranes-web.repl
                                            :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                             :dependencies [[binaryage/devtools "0.9.9"]
                                            [ring/ring-mock "0.3.2"]
                                            [ring/ring-devel "1.6.3"]
                                            [prone "1.5.1"]
                                            [figwheel-sidecar "0.5.15"]
                                            [org.clojure/tools.nrepl "0.2.13"]
                                            [com.cemerick/piggieback "0.2.2"]
                                            [pjstadig/humane-test-output "0.8.3"]

                                            ]

                             :source-paths ["env/dev/clj"]
                             :plugins [[lein-figwheel "0.5.15"]
                                       ]

                             :injections [(require 'pjstadig.humane-test-output)
                                          (pjstadig.humane-test-output/activate!)]

                             :env {:dev true}}
             :project/dev {:resource-paths ["env/dev" "env/dev/resources"]}
             :profiles/dev {}

             :uberjar {:hooks [minify-assets.plugin/hooks]
                       :source-paths ["env/prod/clj"]
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
                       :env {:production true}
                       :aot :all
                       :omit-source true}})
