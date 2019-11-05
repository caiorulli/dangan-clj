(ns dangan-clj.commands.story-test
  (:require [dangan-clj.commands.story :as commands.story]
            [midje.sweet :refer [=> fact]]))

(fact "Should load test story"
  (commands.story/initialize) =>
  {:context {}
   :scene   {:dialogue  [[:thought "Giba's hauntingly neat and organized room."]]
             :name      "Giba's Room"
             :pois      [{:dialogue [["Thiago" "What's that big weird machine?"]
                                     ["Giba" "It's a paper schredder."]
                                     ["Thiago" "Why do you even have that here?"]]
                          :name     "Schredder"}
                         {:clue     {:description "He says it's tomato sauce, but I don't buy it."}
                          :dialogue [["Giba" "That's the knife I used to cut tomatoes."]]
                          :name     "Bloody knife"}
                         {:clue     {:description "Self-explanatory."}
                          :dialogue [["Thiago" "So here's where you hide the bodies."]]
                          :name     "Washing machine"}]
             :presences [{:dialogue [["Giba"
                                      "Have you ever watched Dragon Ball X???"]]
                          :name     "Giba"}
                         {:dialogue [["Thiago" "Yo!"]] :name "Thiago"}]}})
