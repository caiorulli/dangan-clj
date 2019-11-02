(defproject dangan-clj "0.1.0-SNAPSHOT"
  :description "Investigative game framework"
  :url "https://github.com/caiorulli/dangan-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [reagent "0.8.1"]
                 [re-frame "0.10.9"]]

  :source-paths ["src"]

  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" "dangan-clj.test-runner"]}

  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.9.9"]
                                  [com.bhauman/figwheel-main "0.2.3"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]
                   :plugins      [[jonase/eastwood "0.3.5"]
                                  [lein-ancient "0.6.15"]
                                  [lein-cljfmt "0.6.4"]
                                  [lein-cloverage "1.0.13"]
                                  [lein-kibit "0.1.7"]
                                  [lein-midje "3.2.1"]]
                   :cljfmt       {:indents {fact  [[:block 1]]
                                            facts [[:block 1]]}}}})
