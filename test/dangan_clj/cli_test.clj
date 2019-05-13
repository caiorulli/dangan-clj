(ns dangan-clj.cli-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.game-logic :refer [make-initial-state
                                           interact-with
                                           advance-dialog]]
            [dangan-clj.cli :refer [make-prompt
                                    evaluate-command
                                    present-state]]
            [dangan-clj.game.example :refer [test-scene]]))

(def initial-state (make-initial-state test-scene))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (make-prompt initial-state) => "(Giba's House) > ")

 (fact "on dialog mode, should display three dots"
       (let [knife-dialog-state (interact-with initial-state "knife")]
         (make-prompt knife-dialog-state) => "...")))

(facts
 "about evaluating commands"
 (fact "should yield same result from interact-with"
       (let [knife-dialog-state (interact-with initial-state "knife")]
         (evaluate-command initial-state "interact knife") => knife-dialog-state))

 (fact "on dialog mode, any command should trigger dialog advance"
       (let [knife-dialog-state (interact-with initial-state "knife")
             after-dialog-state (advance-dialog knife-dialog-state)]
         (evaluate-command knife-dialog-state "") => after-dialog-state)))

(facts
 "about presenting state"
 (fact "on interactive mode, returns scene description"
       (present-state initial-state "interact knife") => (str "We're in Giba's appartment for the first time. "
                                                              "It seems very organized and clean. "
                                                              "There is a distinctive, disturbing feel to it, though."))

 (fact "on dialog mode, returns the current speaker and text"
       (let [knife-dialog-state (interact-with initial-state "knife")]
         (present-state knife-dialog-state "interact knife")
         =>
         (str "Giba Marçon: "
              "That's the knife I used to cut tomatoes."))))
