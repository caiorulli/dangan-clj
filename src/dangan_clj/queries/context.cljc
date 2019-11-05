(ns dangan-clj.queries.context
  (:require [dangan-clj.queries.story :as queries.story]))

(defn actions [{:keys [context] :as db}]
  (if (contains? context :action/talk)
    (queries.story/characters db)
    (if (contains? context :action/examine)
      (queries.story/pois db)
      #{:action/examine
        :action/talk})))
