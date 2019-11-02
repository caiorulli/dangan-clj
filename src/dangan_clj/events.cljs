(ns dangan-clj.events
  (:require [re-frame.core :as rf]
            [dangan-clj.entrypoint :as entrypoint]
            [dangan-clj.input.example :as example]))

(rf/reg-event-db
  :initialize
  (fn [_ _]
    {:input ""
     :game  (entrypoint/init example/game example/cli-dict)}))

(rf/reg-event-db
  :change-input
  (fn [db [_ new-input]]
    (assoc db :input new-input)))

(defn command-result
  [{:keys [input game]}]
  (let [new-game (entrypoint/exec game input)]
    {:input ""
     :game  new-game}))

(rf/reg-event-db
  :execute-input
  (fn [db _]
    (command-result db)))
