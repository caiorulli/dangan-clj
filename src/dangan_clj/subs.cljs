(ns dangan-clj.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :line
 (fn [_ _]
   "Under construction!"))

(rf/reg-sub
 :actions
 (fn [_ _]
   ["talk to rodrigo" "describe" "go to pool"]))

(rf/reg-sub
 :log
 (fn [db _]
   db))
