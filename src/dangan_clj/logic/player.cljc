(ns dangan-clj.logic.player
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.logic.game :as game]))

(s/def ::clues (s/coll-of ::game/clue-id))
(s/def ::current-scene keyword?)

(s/def ::player (s/keys :req-un [::clues
                                 ::current-scene]))

(defn player [game]
  {:clues         #{}
   :current-scene (:first-scene game)})

(defn current-scene [player game]
  ((:current-scene player) (:scenes game)))

(defn go-to [player new-scene-id game]
  (let [scenes    (:scenes game)
        new-scene (get scenes new-scene-id)]
    (if (nil? new-scene)
      player
      (assoc player :current-scene new-scene-id))))

(defn with-clue [player clue-id]
  (if-not (nil? clue-id)
    (update player :clues #(conj % clue-id))
    player))