(ns dangan-clj.logic.state-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.state :as state]
            [midje.sweet :refer [=> fact facts]]))

(def valid-player {:clues []})
(def valid-state {:game test-game/test-game
                  :mode :interact
                  :player valid-player
                  :current-scene :rodrigos-room})

(facts "about state validation"
       (fact "should contain required fields"
             (s/valid? ::state/state nil) => false
             (s/valid? ::state/state {}) => false

             (s/valid? ::state/state {:game test-game/test-game
                                      :player valid-player}) => false
             (s/valid? ::state/state {:mode :interact
                                      :player valid-player}) => false
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :lala
                                      :player valid-player}) => false
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :interact}) => false
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :interact
                                      :player valid-player}) => false

             (s/valid? ::state/state valid-state) => true)

       (fact "might contain optional fields"
             (s/valid? ::state/state (merge valid-state {:current-dialog :knife-dialog
                                                         :current-line 0})) => true
             (s/valid? ::state/state (merge valid-state {:current-dialog "lala"
                                                         :current-line []})) => false))

(facts "about state functions"
       (fact "makes valid initial state"
             (s/valid? ::state/state
                       (state/initial-state test-game/test-game)) => true))

(facts
 "about initial state"
 (fact "should have correct initial configuration"
       (:mode consts/initial) => :interact))

(facts
    "about the player interaction"
  (fact "player should start clueless"
    (-> consts/initial
        :player
        :clues) => [])

  (fact "player should not be able to add same clue twice"
    (let [already-interacted-state (state/examine consts/initial :knife)]
      (-> (state/examine already-interacted-state :knife)
          :player
          :clues) => [:bloody-knife]))

  (fact "player should add clues by interacting with poi"
    (-> (state/examine consts/initial :knife)
        :player
        :clues) => [:bloody-knife])

  (fact "player examining non-existing poi should not return different player"
    (state/examine consts/initial :balloon) => consts/initial))
