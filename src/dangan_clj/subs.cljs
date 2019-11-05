(ns dangan-clj.subs
  (:require [dangan-clj.queries.context :as queries.context]
            [re-frame.core :as rf]))

(rf/reg-sub
 :line
 (fn [_ _]
   "Under construction!"))

(rf/reg-sub
 :actions
 (fn [db _]
   (queries.context/actions db)))

(rf/reg-sub
 :log
 (fn [db _]
   db))
