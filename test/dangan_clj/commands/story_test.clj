(ns dangan-clj.commands.story-test
  (:require [dangan-clj.commands.story :as commands.story]
            [midje.sweet :refer [=> fact]]))

(fact "Should load test story"
  (commands.story/initialize) =>
  {:scene   {:dialogue  [[:thought "Giba's hauntingly neat and organized room."]]
             :name      "Giba's Room"
             :pois      [{:dialogue [[:thiago "What's that big weird machine?"]
                                     [:giba "It's a paper schredder."]
                                     [:thiago "Why do you even have that here?"]]}
                         {:clue     {:description  "He says it's tomato sauce, but I don't buy it."
                                     :display-name "Bloody knife"}
                          :dialogue [[:giba "That's the knife I used to cut tomatoes."]]}
                         {:clue     {:description "Self-explanatory." :display-name "Schredder"}
                          :dialogue [[:thiago "So here's where you hide the bodies."]]}]
             :presences {:giba [[:giba "Have you ever watched Dragon Ball X???"]]}}
   :context #{}})
