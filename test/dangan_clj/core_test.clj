(ns dangan-clj.core-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.core :refer [make-poi
                                     interact-with
                                     make-initial-state]]))

(def clue-1 {:id 1})

(def knife (make-poi "knife" clue-1
                     "That's a scary knife"))

(def test-scene
  {:pois #{knife}})

(def initial-state (make-initial-state test-scene))

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
       (:mode (interact-with "knife" initial-state)) => :dialog)

 (fact "interaction should trigger text for interaction"
       (:text (interact-with "knife" initial-state)) => "That's a scary knife"))
