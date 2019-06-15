(ns dangan-clj.logic.game-logic)

(defn get-current-scene [{:keys [current-scene]
                          {:keys [scenes]} :game}]
  (current-scene scenes))

(defn- find-poi [{:keys [pois]} poi-id]
  (get pois poi-id))

(defn- add-clue [player clue]
  (if (some (partial = clue) (:clues player))
    player
    (assoc player :clues (conj (:clues player) clue))))

(defn examine [{:keys [player]
                :as state} poi-id]
  (let [current-scene (get-current-scene state)
        poi (find-poi (:game state) poi-id)
        {:keys [clue dialog-id]} poi]
    (if (nil? poi)
      state
      (merge state
             {:player (add-clue player clue)
              :mode :dialog
              :current-dialog dialog-id
              :current-line 0}))))

(defn advance-dialog [{:keys [current-line current-dialog]
                       :as state}]
  (let [next-line (inc current-line)
        dialog (get (:dialogs (:game state)) current-dialog)]
    (if (= next-line (count dialog))
      (assoc state :mode :interact)
      (assoc state :current-line next-line))))
