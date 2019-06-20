(ns dangan-clj.input.consts
  (:require [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.state :as state]))

(def scene-description "Giba's hauntingly neat and organized room.")
(def scene-prompt "(Giba's Room) > ")

(def initial (state/initial-state test-game/test-game))

(def entered-scene-two (state/go-to initial :laundry test-game/test-game))
