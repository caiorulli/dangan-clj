(ns dangan-clj.logic.player
  (:require [clojure.spec.alpha :as s]))

(s/def ::clues vector?)
(s/def ::current-scene keyword?)

(s/def ::player (s/keys :req-un [::clues
                                 ::current-scene]))

(defn player [game]
  {:clues []
   :current-scene (:first-scene game)})

(defn current-scene [player game]
  ((:current-scene player) (:scenes game)))

(defn presence [player character-id game]
  (some #(when (= (first %) character-id) %)
        (:presences (current-scene player game))))

(defn go-to [player new-scene-id game]
  (let [scenes (:scenes game)
        new-scene (get scenes new-scene-id)]
    (if (nil? new-scene)
      player
      (assoc player :current-scene new-scene-id))))

