(ns dangan-clj.events
  (:require [re-frame.core :as rf]
            [dangan-clj.entrypoint :as entrypoint]
            [dangan-clj.input.example :as example]))

(rf/reg-event-db
  :initialize
  (fn [_ _]
    (entrypoint/init example/game)))
