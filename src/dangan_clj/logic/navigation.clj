(ns dangan-clj.logic.navigation)

(defn go-to [state new-scene-id]
  (let [scenes (:scenes (:game state))
        new-scene (get scenes new-scene-id)]
    (if (nil? new-scene)
      state
      (assoc state :current-scene new-scene-id))))
