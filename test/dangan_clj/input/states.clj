(ns dangan-clj.input.states
  (:require [dangan-clj.game-logic :as logic]
            [dangan-clj.logic.navigation :as nav]
            [dangan-clj.input.example :as example]))

(def clue-1 {:id 1})

(def knife
  {:clue     clue-1
   :dialog   [{:speaker "Giba"
               :text    "That's the knife I used to cut tomatoes."}]})

(def schredder
  {:dialog   [{:speaker "Thiago"
               :text    "What's that big weird machine?"}
              {:speaker "Giba"
               :text    "It's a paper schredder."}
              {:speaker "Thiago"
               :text    "Why do you even have that here?"}]})

(def test-scene
  (assoc example/rodrigos-room :pois {:knife     knife
                                      :schredder schredder
                                      :rodrigo   example/rodrigo
                                      :phone     example/phone}))

(def test-game (assoc example/game
                 :scenes
                 {:rodrigos-room test-scene
                  :pool          example/pool}))

(def initial (logic/make-initial-state test-game example/cli-dict))
(def dialog-start (logic/examine initial :knife))
(def entered-scene-two (nav/go-to initial :pool))
