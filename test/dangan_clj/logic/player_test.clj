(ns dangan-clj.logic.player-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.player :as player]
            [midje.sweet :refer [=> fact facts]]))

(def valid-player {:clues []})
(def valid-state {:player valid-player
                  :current-scene :rodrigos-room})

(facts "about player validation"
       (fact "should contain required fields"
             (s/valid? ::player/state nil) => false
             (s/valid? ::player/state {}) => false

             (s/valid? ::player/state {:player valid-player}) => false

             (s/valid? ::player/state valid-state) => true))

(facts "about player functions"
       (fact "makes valid initial player"
             (s/valid? ::player/state
                       (player/initial-state test-game/test-game)) => true))

(facts
 "about the player interaction"
 (fact "player should start clueless"
       (-> consts/initial
           :player
           :clues) => []))

(facts
 "about navigation"
 (fact "navigating to nil should just return same player"
       (player/go-to consts/initial nil test-game/test-game) => consts/initial)

 (fact "navigating to inexisting symbol should also just return player"
       (player/go-to consts/initial :rollercoaster test-game/test-game) => consts/initial)

 (fact "navigating to existing scene should change current scene"
       (-> (player/go-to consts/initial :laundry test-game/test-game)
           :current-scene) => :laundry))
