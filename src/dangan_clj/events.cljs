(ns dangan-clj.events
  (:require [dangan-clj.commands.context :as commands.context]
            [dangan-clj.commands.story :as commands.story]
            [re-frame.core :as rf]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (commands.story/initialize)))

(rf/reg-event-db
 :select-action
 (fn [db [_ action]]
   (commands.context/select-action db action)))
