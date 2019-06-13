(ns dangan-clj.game-logic)

(defn- make-player []
  {:clues #{}})

(defn make-initial-state [game cli-dict]
  {:player        (make-player)
   :game          game
   :mode          :interact
   :current-scene (:first-scene game)
   :cli-dict      cli-dict})

(defn get-current-scene [{:keys [current-scene]
                          {:keys [scenes]} :game}]
  (current-scene scenes))

(defn- find-poi [{:keys [pois]} poi-id]
  (get pois poi-id))

(defn- add-clue [player clue]
  (assoc player :clues (conj (:clues player) clue)))

(defn examine [{:keys [player]
                :as state} poi-id]
  (let [current-scene (get-current-scene state)
        poi (find-poi (:game state) poi-id)
        {:keys [clue dialog]} poi]
    (if (nil? poi)
      state
      (merge state
             {:player (add-clue player clue)
              :mode :dialog
              :dialog dialog
              :line 0}))))

(defn advance-dialog [{:keys [line dialog]
                       :as state}]
  (let [next-line (inc line)]
    (if (= next-line (count dialog))
      (assoc state :mode :interact)
      (assoc state :line next-line))))
