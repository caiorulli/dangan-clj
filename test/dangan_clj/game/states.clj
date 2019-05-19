(ns dangan-clj.game.states
  (:require [dangan-clj.game.example :as game ]
            [dangan-clj.game-logic   :as logic]))

(def clue-1 {:id 1})

(def knife
  {:name "knife"
   :clue clue-1
   :dialog [{:speaker "Giba"
             :text    "That's the knife I used to cut tomatoes."}]})

(def schredder
  {:name "schredder"
   :dialog [{:speaker "Thiago"
              :text    "What's that big weird machine?"}
             {:speaker "Giba"
              :text    "It's a paper schredder."}
             {:speaker "Thiago"
              :text    "Why do you even have that here?"}]})

(def test-scene
  (assoc game/rodrigos-room :pois #{knife
                                  schredder}))

(def initial (logic/make-initial-state test-scene))
(def dialog-start (logic/examine initial "knife"))
