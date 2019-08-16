(ns dangan-clj.cli.dict-test
  (:require [midje.sweet :refer [fact facts =>]]
            [clojure.spec.alpha :as s]
            [dangan-clj.cli.dict :as dict]))

(facts "about the cli dictionary"
  (fact "should conform to spec"
    (s/valid? ::dict/cli-dict nil) => false
    (s/valid? ::dict/cli-dict {}) => true
    (s/valid? ::dict/cli-dict {"lala" :lala}) => false
    (s/valid? ::dict/cli-dict {:lala #{"lala" "lalala"}}) => true))

