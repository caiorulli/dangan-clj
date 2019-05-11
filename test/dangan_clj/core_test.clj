(ns dangan-clj.core-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.core :refer [make-poi
                                     interact-with
                                     make-initial-state
                                     advance-dialog]]))

(def clue-1 {:id 1})

(def knife
  (make-poi "knife"
            clue-1
            [{:speaker "Giba Marçon"
              :text    "That's the knife I used to cut tomatoes."}]))

(def schredder
  (make-poi "schredder"
            clue-1
            [{:speaker "Thiago Feliciano"
              :text    "What's that big weird machine?"}
             {:speaker "Giba Marçon"
              :text    "It's a paper schredder."}
             {:speaker "Thiago Feliciano"
              :text    "Why do you even have that here?"}]))

(def test-scene
  {:pois #{knife
            schredder}})

(def initial-state (make-initial-state test-scene))

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
       (let [already-interacted-state (interact-with "knife" initial-state)]
         (-> (interact-with "knife" already-interacted-state)
             :player
             :clues) => #{clue-1}))

 (fact "player should add clues by interacting with poi"
       (-> (interact-with "knife" initial-state)
           :player
           :clues) => #{clue-1})

 (fact "player interacting with non-existing poi should not return different player"
       (interact-with "balloon" initial-state) => initial-state))

(facts
 "about dialog mode"
 (fact "interaction should trigger dialog mode"
       (let [dialog-mode-state (interact-with "knife" initial-state)]
         (:mode    dialog-mode-state) => :dialog
         (:speaker dialog-mode-state) => "Giba Marçon"
         (:text    dialog-mode-state) => "That's the knife I used to cut tomatoes."))

 (fact "after dialog is complete, 'advance-dialog' command should go back to interact mode"
       (let [dialog-mode-state (interact-with "knife" initial-state)
             dialog-finished-state (advance-dialog dialog-mode-state)]
         (:mode    dialog-finished-state) => :interact
         (:speaker dialog-finished-state) => nil
         (:text    dialog-finished-state) => nil))

 (fact "'advance-dialog' command should trigger next line if dialog has more than one line"
       (let [schredder-line-one (interact-with "schredder" initial-state)
             schredder-line-two   (advance-dialog schredder-line-one)
             schredder-line-three (advance-dialog schredder-line-two)
             schredder-dialog-end (advance-dialog schredder-line-three)]
         (:mode    schredder-line-one) => :dialog
         (:speaker schredder-line-one) => "Thiago Feliciano"
         (:text    schredder-line-one) => "What's that big weird machine?"

         (:mode    schredder-line-two) => :dialog)))
