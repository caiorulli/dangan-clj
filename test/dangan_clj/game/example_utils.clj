(ns dangan-clj.game.example-utils
  (:require [dangan-clj.game.example :as game ]
            [dangan-clj.game-logic   :as logic]))

;; Constants of determined example game data

(def scene-description (:description game/test-scene))

(def initial-state
  (logic/make-initial-state game/test-scene))
(def knife-dialog-state
  (logic/interact-with initial-state "knife"))

(def knife-dialog
  (:dialog game/knife))
