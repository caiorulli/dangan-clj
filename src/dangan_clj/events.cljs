(ns dangan-clj.events
  (:require [dangan-clj.commands.story :as commands.story]
            [re-frame.core :as rf]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (commands.story/initialize)))
