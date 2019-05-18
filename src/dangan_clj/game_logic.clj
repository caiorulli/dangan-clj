(ns dangan-clj.game-logic
  (:require [clojure.set :refer [select]]))

(defn- make-player []
  {:clues #{}})

(defn make-poi [name clue dialog]
  {:name name :clue clue :dialog dialog})

(defn make-initial-state [scene]
  {:player (make-player)
   :scene scene
   :mode :interact})

(defn- find-poi [state poi-name]
  (first (select #(= (:name %) poi-name)
                 (:pois (:scene state)))))

(defn- add-clue [player poi]
  (assoc player :clues (conj (:clues player) (:clue poi))))

(defn examine [state poi-name]
  (let [poi (find-poi state poi-name)]
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
