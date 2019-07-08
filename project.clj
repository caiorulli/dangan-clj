(defproject dangan-clj "0.1.0-SNAPSHOT"
  :description "Investigative game framework"
  :url "https://github.com/caiorulli/dangan-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot dangan-clj.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.9.8"]]
                   :plugins      [[jonase/eastwood "0.3.5"]
                                  [lein-ancient "0.6.15"]
                                  [lein-cljfmt "0.6.4"]
                                  [lein-cloverage "1.0.13"]
                                  [lein-kibit "0.1.6"]
                                  [lein-midje "3.2.1"]]
                   :cljfmt       {:indents {fact  [[:block 1]]
                                            facts [[:block 1]]}}}
             :uberjar {:aot :all}})
