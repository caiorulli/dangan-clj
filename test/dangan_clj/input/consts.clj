(ns dangan-clj.input.consts
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.player :as player]))

(def scene-description "Giba's hauntingly neat and organized room.")
(def scene-prompt "(Giba's Room) > ")

(def initial (player/initial-state test-game/test-game))

(def initial-cli (cli/cli test-game/test-game))

(def entered-scene-two (player/go-to initial :laundry test-game/test-game))
