(ns dangan-clj.game-logic
  (:require [clojure.set :refer [select]]))

(defn- make-player []
  {:clues #{}})

(defn make-initial-state [game]
  {:player        (make-player)
   :game          game
   :mode          :interact
   :current-scene (:first-scene game)})

(defn get-current-scene [state]
  (let [scenes (:scenes (:game state))
        current-scene-id (:current-scene state)]
    (first (select #(= (:id %) current-scene-id) scenes))))

(defn- find-poi [state poi-id]
  (get (:pois (get-current-scene state)) poi-id))

(defn- add-clue [player poi]
  (assoc player :clues (conj (:clues player) (:clue poi))))

(defn examine [state poi-id]
  (let [poi (find-poi state poi-id)]
    (if (nil? poi)
      state
      (merge state
             {:player (add-clue (:player state) poi)
              :mode :dialog
              :dialog (:dialog poi)
              :line 0}))))

(defn advance-dialog [state]
  (let [next-line (inc (:line state))
        dialog (:dialog state)]
    (if (= next-line (count dialog))
      (assoc state :mode :interact)
      (assoc state :line next-line))))
