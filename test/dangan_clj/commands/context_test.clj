(ns dangan-clj.commands.context-test
  (:require [dangan-clj.commands.context :as commands.context]
            [dangan-clj.commands.story :as commands.story]
            [midje.sweet :refer [=> fact]]))

(fact "adds action to context"
  (let [db (commands.context/select-action (commands.story/initialize)
                                           :action/talk)]
    (:context db) => #{:action/talk})

  (let [db (commands.context/select-action (commands.story/initialize)
                                           :action/examine)]
    (:context db) => #{:action/examine}))
