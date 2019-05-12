(ns dangan-clj.cli-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.game-logic :refer [make-initial-state
                                           interact-with]]
            [dangan-clj.cli :refer [make-prompt
                                    evaluate-command
                                    present-state]]
            [dangan-clj.game.example :refer [test-scene]]))

(def initial-state (make-initial-state test-scene))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (make-prompt initial-state) => "(Giba's House) > "))

(facts
 "about evaluating commands"
 (fact "should yield same result from interact-with"
       (let [knife-dialog-state (interact-with "knife" initial-state)]
         (evaluate-command initial-state "knife") => knife-dialog-state)))

(facts
 "about presenting state"
 (fact "on interactive mode, returns state"
       (present-state initial-state) => initial-state)

 (fact "on dialog mode, returns the current speaker and text"
       (let [knife-dialog-state (interact-with "knife" initial-state)]
         (present-state knife-dialog-state)
         =>
         (str "Giba Marçon: "
              "That's the knife I used to cut tomatoes."))))
