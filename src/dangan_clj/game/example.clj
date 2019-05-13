(ns dangan-clj.game.example
  (:require [dangan-clj.game-logic :refer [make-poi]]))

(def clue-1 {:id 1})

(def knife
  (make-poi "knife"
            clue-1
            [{:speaker "Giba Marçon"
              :text    "That's the knife I used to cut tomatoes."}]))

(def schredder
  (make-poi "schredder"
            clue-1
            [{:speaker "Thiago Feliciano"
              :text    "What's that big weird machine?"}
             {:speaker "Giba Marçon"
              :text    "It's a paper schredder."}
             {:speaker "Thiago Feliciano"
              :text    "Why do you even have that here?"}]))

(def test-scene
  {:name "Giba's House"
   :description (str "We're in Giba's appartment for the first time. "
                     "It seems very organized and clean. "
                     "There is a distinctive, disturbing feel to it, though.")
   :pois #{knife
            schredder}})
