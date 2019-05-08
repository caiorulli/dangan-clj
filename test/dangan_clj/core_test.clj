(ns dangan-clj.core-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.core :refer [make-player
                                     make-poi
                                     interact-with
                                     interact]]))

(def clue-1 {:id 1})

(def knife (make-poi "knife" clue-1))

(def test-scene
  {:pois #{knife}})

(facts "about the player interaction"
       (fact "player should start clueless"
             (:clues (make-player)) => #{})

       (fact "player should not be able to add same clue twice"
             (let [player (make-player)
                   player-with-clue (interact player knife)]
               (:clues (interact player-with-clue knife)) => #{clue-1}))

       (fact "player should add clues by interacting with poi"
             (let [player (make-player)]
               (:clues (interact-with "knife" player test-scene)) => #{clue-1})))
