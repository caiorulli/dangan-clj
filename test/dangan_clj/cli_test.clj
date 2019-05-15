(ns dangan-clj.cli-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.game-logic :refer [make-initial-state
                                           advance-dialog]]
            [dangan-clj.cli :refer [make-prompt
                                    evaluate-command
                                    present-state
                                    help-text]]
            [dangan-clj.game.example :refer [test-scene]]
            [dangan-clj.game.example-utils :as example-utils]
            [dangan-clj.game.states :as states]))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (make-prompt states/initial) => "(Giba's House) > ")

 (fact "on dialog mode, should display three dots"
       (make-prompt states/knife-dialog) => "..."))

(facts
 "about evaluating commands"
 (fact "should yield same result from interact-with"
       (evaluate-command states/initial "examine knife") => states/knife-dialog)

 (fact "on dialog mode, any command should trigger dialog advance"
       (let [after-dialog-state (advance-dialog states/knife-dialog)]
         (evaluate-command states/knife-dialog "") => after-dialog-state))

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
       (present-state states/knife-dialog "examine knife")
       =>
       (str "Giba: "
            "That's the knife I used to cut tomatoes."))

 (fact "look command should output scene description"
       (present-state states/initial "describe") => example-utils/scene-description)

 (fact "help command should output help text"
       (present-state states/initial "help") => help-text))
