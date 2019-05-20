(ns dangan-clj.logic.navigation
  (:require [clojure.set :refer [select]]))

(defn go-to [state new-scene-id]
  (let [scenes (:scenes (:game state))
        new-scene (first (select #(= (:id %) new-scene-id) scenes))]
    (if (nil? new-scene)
      state
      (assoc state :current-scene new-scene-id))))
