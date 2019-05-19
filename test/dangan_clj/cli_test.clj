(ns dangan-clj.cli-test
  (:require [dangan-clj
             [cli :refer [evaluate-command help-text make-prompt present-state]]
             [game-logic :refer [advance-dialog]]]
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
 "about evaluating commands"
 (fact "should yield same result from interact-with"
       (evaluate-command states/initial consts/start-dialog-command) => states/dialog-start)

 (fact "on dialog mode, any command should trigger dialog advance"
       (let [after-dialog-state (advance-dialog states/dialog-start)]
         (evaluate-command states/dialog-start "") => after-dialog-state))

 (fact "certain commands should not trigger state changes in interact mode"
       (let [evaluate #(evaluate-command states/initial %)]
         (evaluate "describe") => states/initial
         (evaluate "help") => states/initial
         (evaluate "knife") => states/initial
         (evaluate "") => states/initial)))

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
