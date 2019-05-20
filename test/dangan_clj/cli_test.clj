(ns dangan-clj.cli-test
  (:require [dangan-clj.cli :refer [help-text make-prompt present-state]]
            [dangan-clj.game
             [consts :as consts]
             [states :as states]]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (make-prompt states/initial) => consts/scene-prompt)

 (fact "on dialog mode, should display three dots"
       (make-prompt states/dialog-start) => "..."))

(facts
 "about presenting state"
 (fact "on interactive mode, returns nothing"
       (present-state states/initial "") => nil)

 (fact "on dialog mode, returns the current speaker and text"
       (present-state states/dialog-start
                      consts/start-dialog-command) => consts/formatted-dialog-line)

 (fact "look command should output scene description"
       (present-state states/initial "describe") => consts/scene-description)

 (fact "help command should output help text"
       (present-state states/initial "help") => help-text))
