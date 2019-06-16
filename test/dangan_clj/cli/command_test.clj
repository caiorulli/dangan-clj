(ns dangan-clj.cli.command-test
  (:require  [midje.sweet :refer [fact facts =>]]
             [clojure.spec.alpha :as s]
             [dangan-clj.cli.command :as command]))

(facts "about command validation"
  (fact "should always have type"
    (s/valid? ::command/command nil) => false
    (s/valid? ::command/command {}) => false
    (s/valid? ::command/command {:type :lala}) => false

    (s/valid? ::command/command {:type :describe}) => true
    (s/valid? ::command/command {:type :examine}) => true
    (s/valid? ::command/command {:type :help}) => true)

  (fact "may have a target id"
    (s/valid? ::command/command {:type :examine
                                 :target :spectreman}) => true
    (s/valid? ::command/command {:type :examine
                                 :target "conquista"}) => false))

