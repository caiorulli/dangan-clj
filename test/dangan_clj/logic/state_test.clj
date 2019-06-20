(ns dangan-clj.logic.state-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.state :as state]
            [midje.sweet :refer [=> fact facts]]))

(def valid-player {:clues []})
(def valid-state {:player valid-player
                  :current-scene :rodrigos-room})

(facts "about state validation"
       (fact "should contain required fields"
             (s/valid? ::state/state nil) => false
             (s/valid? ::state/state {}) => false

             (s/valid? ::state/state {:player valid-player}) => false

             (s/valid? ::state/state valid-state) => true))

(facts "about state functions"
       (fact "makes valid initial state"
             (s/valid? ::state/state
                       (state/initial-state test-game/test-game)) => true))

(facts
 "about the player interaction"
 (fact "player should start clueless"
       (-> consts/initial
           :player
           :clues) => []))

(facts
 "about navigation"
 (fact "navigating to nil should just return same state"
       (state/go-to consts/initial nil test-game/test-game) => consts/initial)

 (fact "navigating to inexisting symbol should also just return state"
       (state/go-to consts/initial :rollercoaster test-game/test-game) => consts/initial)

 (fact "navigating to existing scene should change current scene"
       (-> (state/go-to consts/initial :laundry test-game/test-game)
           :current-scene) => :laundry))
