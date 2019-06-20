(ns dangan-clj.logic.navigation-test
  (:require [dangan-clj.input.consts :as consts]
            [dangan-clj.logic.navigation :as nav]
            [midje.sweet :refer [=> fact facts]]
            [dangan-clj.input.test-game :as test-game]))

(facts
 "about navigation"
 (fact "navigating to nil should just return same state"
       (nav/go-to consts/initial nil test-game/test-game) => consts/initial)

 (fact "navigating to inexisting symbol should also just return state"
       (nav/go-to consts/initial :rollercoaster test-game/test-game) => consts/initial)

 (fact "navigating to existing scene should change current scene"
       (-> (nav/go-to consts/initial :laundry test-game/test-game)
           :current-scene) => :laundry))
