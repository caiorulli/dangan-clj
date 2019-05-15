(ns dangan-clj.game-logic-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.game-logic :as logic]
            [dangan-clj.game.example :refer [test-scene
                                             clue-1]]))

(def initial-state (logic/make-initial-state test-scene))

(facts
 "about initial state"
 (fact "should have correct initial configuration"
       (:mode initial-state) => :interact
       (:text initial-state) => nil
       (:speaker initial-state) => nil))

(facts
 "about the player interaction"
 (fact "player should start clueless"
       (-> initial-state
           :player
           :clues) => #{})

 (fact "player should not be able to add same clue twice"
       (let [already-interacted-state (logic/examine initial-state "knife")]
         (-> (logic/examine already-interacted-state "knife")
             :player
             :clues) => #{clue-1}))

 (fact "player should add clues by interacting with poi"
       (-> (logic/examine initial-state "knife")
           :player
           :clues) => #{clue-1})

 (fact "player examining non-existing poi should not return different player"
       (logic/examine initial-state "balloon") => initial-state))

(facts
 "about dialog mode"
 (fact "interaction should trigger dialog mode"
       (let [dialog-mode-state (logic/examine initial-state "knife")]
         (:mode    dialog-mode-state) => :dialog
         (:speaker dialog-mode-state) => "Giba"
         (:text    dialog-mode-state) => "That's the knife I used to cut tomatoes."))

 (fact "after dialog is complete, 'advance-dialog' command should go back to interact mode"
       (let [dialog-mode-state (logic/examine initial-state "knife")
             dialog-finished-state (logic/advance-dialog dialog-mode-state)]
         (:mode    dialog-finished-state) => :interact
         (:speaker dialog-finished-state) => nil
         (:text    dialog-finished-state) => nil))

 (fact "'advance-dialog' command should trigger next line if dialog has more than one line"
       (let [schredder-line-one   (logic/examine initial-state "schredder")
             schredder-line-two   (logic/advance-dialog schredder-line-one)
             schredder-line-three (logic/advance-dialog schredder-line-two)
             schredder-dialog-end (logic/advance-dialog schredder-line-three)]
         (:mode    schredder-line-one) => :dialog
         (:speaker schredder-line-one) => "Thiago"
         (:text    schredder-line-one) => "What's that big weird machine?"

         (:mode    schredder-line-two) => :dialog
         (:speaker schredder-line-two) => "Giba"
         (:text    schredder-line-two) => "It's a paper schredder."

         (:mode    schredder-line-three) => :dialog
         (:speaker schredder-line-three) => "Thiago"
         (:text    schredder-line-three) => "Why do you even have that here?"

         (:mode    schredder-dialog-end) => :interact
         (:speaker schredder-dialog-end) => nil
         (:text    schredder-dialog-end) => nil)))
