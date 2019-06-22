(ns dangan-clj.input.consts
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.player :as player]))

(def initial-player
  (player/player test-game/test-game))

(def initial-cli
  (cli/cli test-game/test-game))

(def entered-scene-two
  (player/go-to initial-player :laundry test-game/test-game))
