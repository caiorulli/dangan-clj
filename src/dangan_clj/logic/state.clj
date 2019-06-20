(ns dangan-clj.logic.state
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.logic.game :as game]))

(s/def ::clues vector?)
(s/def ::player (s/keys :req-un [::clues]))
(s/def ::current-scene keyword?)

(s/def ::state (s/keys :req-un [::game/game
                                ::player
                                ::current-scene]))

(defn- player []
  {:clues []})

(defn initial-state [game]
  {:player        (player)
   :game          game
   :current-scene (:first-scene game)})

(defn current-scene [state]
  ((:current-scene state) (-> state :game :scenes)))

(defn- enter-dialog [state dialog-id]
  (merge state {:mode :dialog
                :current-dialog dialog-id
                :current-line 0}))

(defn presence [state character-id]
  (some #(when (= (first %) character-id) %)
        (:presences (current-scene state))))

(defn- add-clue [player clue]
  (if (some (partial = clue) (:clues player))
    player
    (assoc player :clues (conj (:clues player) clue))))

(defn examine [{:keys [player]
                :as state} element-id]
  (let [poi (-> state :game :pois (get element-id))
        character (-> state :game :characters (get element-id))
        presence (presence state element-id)]
    (cond
      (not (nil? poi))
      (-> state
          (assoc :player (add-clue player (:clue-id poi))))
      (not (or (nil? character) (nil? presence)))
      state
      :else
      state)))
