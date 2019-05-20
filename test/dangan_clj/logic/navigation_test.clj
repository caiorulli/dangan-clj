(ns dangan-clj.logic.navigation-test
  (:require [midje.sweet :refer [facts fact =>]]
            [dangan-clj.game.states :as states]
            [dangan-clj.logic.navigation :as nav]))

(facts
 "about navigation"
 (fact "navigating to nil should just return same state"
       (nav/go-to states/initial nil) => states/initial)

 (fact "navigating to inexisting symbol should also just return state"
       (nav/go-to states/initial :rollercoaster) => states/initial)

 (fact "navigating to existing scene should change current scene"
       (-> (nav/go-to states/initial :pool)
           :current-scene) => :pool))
