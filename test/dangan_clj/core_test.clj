(ns dangan-clj.core-test
  (:require [midje.sweet :refer [fact facts =>]]
            [dangan-clj.core :refer [make-player
                                     make-poi
                                     interact]]))

(def clue-1 {:id 1})

(defn make-test-poi []
  (make-poi "a bloody knife" clue-1))

(facts "about the player interactiom"
       (fact "player should start clueless"
             (:clues (make-player)) => #{})

       (fact "player should add clues by interacting with scene"
             (let [player (make-player)
                   poi (make-test-poi)]
               (interact player poi) => {:clues #{clue-1}}))

       (fact "player should not be able to add same clue twice"
             (let [player (make-player)
                   poi (make-test-poi)
                   player-with-clue (interact player poi)]
               (interact player-with-clue poi) => {:clues #{clue-1}})))
