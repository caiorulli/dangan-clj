(ns dangan-clj.commands.context)

(defn select-action
  [db action]
  (if (nil? (-> db :context :action))
    (update db :context assoc :action action)
    (update db :context assoc :target action)))
