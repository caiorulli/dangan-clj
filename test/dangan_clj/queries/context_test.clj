(ns dangan-clj.queries.context-test
  (:require [dangan-clj.queries.context :as queries.context]
            [dangan-clj.commands.context :as commands.context]
            [dangan-clj.commands.story :as commands.story]
            [midje.sweet :refer [=> fact]]))

(fact "fetch context actions when there's no context"
  (let [db (commands.story/initialize)]
    (queries.context/actions db) => #{:action/examine
                                      :action/talk}))

(fact "fetch context actions when in talk context"
  (let [db (commands.context/select-action (commands.story/initialize)
                                           :action/talk)]
    (queries.context/actions db) => #{"Thiago"
                                      "Giba"}))

(fact "fetch context actions when in talk context"
  (let [db (commands.context/select-action (commands.story/initialize)
                                           :action/examine)]
    (queries.context/actions db) => #{"Schredder"
                                      "Bloody knife"
                                      "Washing machine"}))
