(ns dangan-clj.commands.context)

(defn select-action
  [db action]
  (update db :context conj action))
