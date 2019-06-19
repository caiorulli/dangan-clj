(ns dangan-clj.logic.state
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.logic.game :as game]))

(s/def ::clues vector?)
(s/def ::player (s/keys :req-un [::clues]))

(s/def ::current-scene keyword?)
(s/def ::mode #{:interact :dialog})

(s/def ::current-dialog keyword?)
(s/def ::current-line int?)

(s/def ::state (s/keys :req-un [::game/game
                                ::mode
                                ::player
                                ::current-scene]
                       :opt-un [::current-dialog
                                ::current-line]))

(defn- player []
  {:clues []})

(defn initial-state [game]
  {:player        (player)
   :game          game
   :mode          :interact
   :current-scene (:first-scene game)})

(defn current-scene [state]
  ((:current-scene state) (-> state :game :scenes)))

(defn- add-clue [player clue]
  (if (some (partial = clue) (:clues player))
    player
    (assoc player :clues (conj (:clues player) clue))))

(defn- enter-dialog [state dialog-id]
  (merge state {:mode :dialog
                :current-dialog dialog-id
                :current-line 0}))

(defn- find-presence [state character-id]
  (some #(when (= (first %) character-id) %)
        (:presences (current-scene state))))

(defn examine [{:keys [player]
                :as state} element-id]
  (let [poi (-> state :game :pois (get element-id))
        character (-> state :game :characters (get element-id))
        presence (find-presence state element-id)]
    (cond
      (not (nil? poi))
      (-> state
          (assoc :player (add-clue player (:clue-id poi)))
          (enter-dialog (:dialog-id poi)))
      (not (or (nil? character) (nil? presence)))
      (enter-dialog state (:dialog-id character))
      :else
      state)))

(defn describe [state]
  (let [current-scene (current-scene state)
        dialog-id (:dialog-id current-scene)]
    (enter-dialog state dialog-id)))

(defn talk-to [state character-id]
  (let [target-presence (find-presence state character-id)]
    (if-not (nil? target-presence)
      (enter-dialog state (target-presence 1))
      state)))

(defn advance-dialog [{:keys [current-line current-dialog]
                       :as state}]
  (let [next-line (inc current-line)
        dialog (-> state :game :dialogs (get current-dialog))]
    (if (= next-line (count dialog))
      (assoc state :mode :interact)
      (assoc state :current-line next-line))))
