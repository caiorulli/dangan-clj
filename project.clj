(defproject dangan-clj "0.1.0-SNAPSHOT"
  :description "Investigative game framework"
  :url "https://github.com/caiorulli/dangan-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot dangan-clj.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.9.8"]]}
             :uberjar {:aot :all}})
