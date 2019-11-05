(ns dangan-clj.queries.story)

(defn characters [db]
  (set (map :name (-> db :scene :presences))))

(defn pois [db]
  (set (map :name (-> db :scene :pois))))
