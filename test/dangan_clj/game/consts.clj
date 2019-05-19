(ns dangan-clj.game.consts
  (:require [dangan-clj.game.example :as game]))

(def scene-description (:description game/test-scene))
(def scene-prompt "(Rodrigo's Room) > ")
(def start-dialog-command "examine knife")
(def formatted-dialog-line "Giba: That's the knife I used to cut tomatoes.")
(def single-line-poi "knife")
(def multi-line-poi "schredder")
