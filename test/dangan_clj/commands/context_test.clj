(ns dangan-clj.commands.context-test
  (:require [dangan-clj.commands.context :as commands.context]
            [dangan-clj.commands.story :as commands.story]
            [midje.sweet :refer [=> fact]]))

(fact "adds action to context"
  (let [db (commands.context/select-action (commands.story/initialize)
                                           :action/talk)]
    (:context db) => {:action :action/talk})

  (let [db (commands.context/select-action (commands.story/initialize)
                                           :action/examine)]
    (:context db) => {:action :action/examine}))

(fact "if context has action, puts target into context"
  (let [db (-> (commands.story/initialize)
               (commands.context/select-action :action/talk)
               (commands.context/select-action "Giba"))]
    (:context db) => {:action :action/talk
                      :target "Giba"})

  (let [db (-> (commands.story/initialize)
               (commands.context/select-action :action/examine)
               (commands.context/select-action "Bloody knife"))]
    (:context db) => {:action :action/examine
                      :target "Bloody knife"}))
