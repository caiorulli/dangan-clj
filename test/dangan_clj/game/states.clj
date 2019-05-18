(ns dangan-clj.game.states
  (:require [dangan-clj.game.example :as game ]
            [dangan-clj.game-logic   :as logic]))

(def initial (logic/make-initial-state game/test-scene))
(def dialog-start (logic/examine initial "knife"))
