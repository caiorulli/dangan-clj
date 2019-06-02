(ns dangan-clj.cli.prompt-test
  (:require [dangan-clj.cli.cli :refer [make-prompt]]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.states :as states]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (make-prompt states/initial) => consts/scene-prompt)

 (fact "on dialog mode, should display three dots"
       (make-prompt states/dialog-start) => "..."))
