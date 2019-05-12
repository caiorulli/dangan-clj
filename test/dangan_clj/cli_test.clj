(ns dangan-clj.cli-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.cli :refer [make-prompt
                                    evaluate-command
                                    present-state]]))

(facts
 "about prompt generation"
 (fact "returns hardcoded string"
       (make-prompt {}) => "prompt -> "))

(facts
 "about evaluating commands"
 (fact "always returns state"
       (evaluate-command {} "lala") => {}))

(facts
 "about presenting state"
 (fact "always returns state"
       (present-state {}) => {}))
