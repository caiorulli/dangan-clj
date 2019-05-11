(ns dangan-clj.core-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.core :refer [make-poi
                                     interact-with
                                     make-initial-state
                                     advance-dialog]]))

(def clue-1 {:id 1})

(def knife-dialog [{:speaker "Giba Marçon"
                    :text "That's the knife I used to cut tomatoes."}])

(def knife (make-poi "knife"
                     clue-1
                     knife-dialog))

(def test-scene
  {:pois #{knife}})

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
         (:mode dialog-mode-state) => :dialog
         (:speaker dialog-mode-state) => "Giba Marçon"
         (:text dialog-mode-state) => "That's the knife I used to cut tomatoes."))

 (fact "after dialog is complete, 'advance-dialog' command should go back to interact mode"
       (let [dialog-mode-state (interact-with "knife" initial-state)]
         (:mode (advance-dialog dialog-mode-state)) => :interact)))
