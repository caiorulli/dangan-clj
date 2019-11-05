(ns dangan-clj.queries.context
  (:require [dangan-clj.queries.story :as queries.story]))

(defn actions [{:keys [context] :as db}]
  (case (:action context)
    :action/talk (queries.story/characters db)
    :action/examine (queries.story/pois db)
    #{:action/examine
      :action/talk}))
