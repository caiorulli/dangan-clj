(ns dangan-clj.subs
  (:require [dangan-clj.entrypoint :as entrypoint]
            [re-frame.core :as rf]))

(rf/reg-sub
  :line
  (fn [_ _]
    "Under construction!"))

(rf/reg-sub
  :actions
  (fn [_ _]
    ["talk to rodrigo" "describe" "go to pool"]))
