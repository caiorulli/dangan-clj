(ns dangan-clj.input.consts
  (:require [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.game-logic :as logic]
            [dangan-clj.logic.navigation :as nav]))

(def scene-description "Giba's hauntingly neat and organized room.")
(def scene-prompt "(Giba's Room) > ")
(def start-dialog-command {:type :examine
                           :target :knife})
(def formatted-dialog-line "Giba: That's the knife I used to cut tomatoes.")
(def single-line-poi :knife)
(def multi-line-poi :schredder)

(def initial (logic/make-initial-state test-game/test-game test-game/cli-dict))

(def dialog-start (logic/examine initial :knife))

(def entered-scene-two (nav/go-to initial :laundry))
