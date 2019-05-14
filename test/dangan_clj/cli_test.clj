(ns dangan-clj.cli-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.game-logic :refer [make-initial-state
                                           interact-with
                                           advance-dialog]]
            [dangan-clj.cli :refer [make-prompt
                                    evaluate-command
                                    present-state]]
            [dangan-clj.game.example :refer [test-scene]]
            [dangan-clj.game.example-utils :as example-utils]))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (make-prompt example-utils/initial-state) => "(Giba's House) > ")

 (fact "on dialog mode, should display three dots"
       (make-prompt example-utils/knife-dialog-state) => "..."))

(facts
 "about evaluating commands"
 (fact "should yield same result from interact-with"
       (evaluate-command example-utils/initial-state "interact knife") => example-utils/knife-dialog-state)

 (fact "on dialog mode, any command should trigger dialog advance"
       (let [after-dialog-state (advance-dialog example-utils/knife-dialog-state)]
         (evaluate-command example-utils/knife-dialog-state "") => after-dialog-state)))

(facts
 "about presenting state"
 (fact "on interactive mode, returns scene description"
       (present-state example-utils/initial-state "") => example-utils/scene-description)

 (fact "on dialog mode, returns the current speaker and text"
       (present-state example-utils/knife-dialog-state "interact knife")
       =>
       (str "Giba Marçon: "
            "That's the knife I used to cut tomatoes."))

 (fact "look command should output all pois of scene"
       (present-state example-utils/initial-state "look") => "knife schredder"))
