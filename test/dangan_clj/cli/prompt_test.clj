(ns dangan-clj.cli.prompt-test
  (:require [dangan-clj.cli.cli :refer [prompt]]
            [dangan-clj.input.consts :as consts]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (prompt consts/initial) => consts/scene-prompt)

 (fact "on dialog mode, should display three dots"
       (prompt consts/dialog-start) => "..."))
