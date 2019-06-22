(ns dangan-clj.logic.player
  (:require [clojure.spec.alpha :as s]))

(s/def ::clues vector?)
(s/def ::current-scene keyword?)

(s/def ::player (s/keys :req-un [::clues
                                 ::current-scene]))

(defn initial-state [game]
  {:clues []
   :current-scene (:first-scene game)})

(defn current-scene [state game]
  ((:current-scene state) (:scenes game)))

(defn presence [state character-id game]
  (some #(when (= (first %) character-id) %)
        (:presences (current-scene state game))))

(defn go-to [state new-scene-id game]
  (let [scenes (:scenes game)
        new-scene (get scenes new-scene-id)]
    (if (nil? new-scene)
      state
      (assoc state :current-scene new-scene-id))))

