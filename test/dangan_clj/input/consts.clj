(ns dangan-clj.input.consts
  (:require [dangan-clj.input.example :as example]))

(def scene-description (:description example/rodrigos-room))
(def scene-prompt "(Rodrigo's Room) > ")
(def start-dialog-command {:type :examine
                           :target :knife})
(def formatted-dialog-line "Giba: That's the knife I used to cut tomatoes.")
(def single-line-poi :knife)
(def multi-line-poi :schredder)
