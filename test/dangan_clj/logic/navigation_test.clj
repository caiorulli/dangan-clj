(ns dangan-clj.logic.navigation-test
  (:require [dangan-clj.input.consts :as consts]
            [dangan-clj.logic.navigation :as nav]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about navigation"
 (fact "navigating to nil should just return same state"
       (nav/go-to consts/initial nil) => consts/initial)

 (fact "navigating to inexisting symbol should also just return state"
       (nav/go-to consts/initial :rollercoaster) => consts/initial)

 (fact "navigating to existing scene should change current scene"
       (-> (nav/go-to consts/initial :laundry)
           :current-scene) => :laundry))
