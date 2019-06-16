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
                  :current-scene :rodrigos-room
                  :cli-dict {:lala #{"lala" "lalala"}}})

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
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :interact
                                      :player valid-player
                                      :current-scene :rodrigos-room}) => false

             (s/valid? ::state/state valid-state) => true)

       (fact "might contain optional fields"
             (s/valid? ::state/state (merge valid-state {:current-dialog :knife-dialog
                                                         :current-line 0})) => true
             (s/valid? ::state/state (merge valid-state {:current-dialog "lala"
                                                         :current-line []})) => false))

(facts "about state functions"
       (fact "makes valid initial state"
             (s/valid? ::state/state (state/make-initial-state test-game/test-game
                                                               test-game/cli-dict)) => true))

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
       (let [already-interacted-state (state/examine consts/initial consts/single-line-poi)]
         (-> (state/examine already-interacted-state consts/single-line-poi)
             :player
             :clues) => [test-game/clue-1]))

 (fact "player should add clues by interacting with poi"
       (-> (state/examine consts/initial consts/single-line-poi)
           :player
           :clues) => [test-game/clue-1])

 (fact "player examining non-existing poi should not return different player"
       (state/examine consts/initial "balloon") => consts/initial))

(facts
 "about dialog mode"
 (fact "interaction should trigger dialog mode"
       (let [dialog-mode-state (state/examine consts/initial consts/single-line-poi)]
         (:mode dialog-mode-state) => :dialog
         (:current-line dialog-mode-state) => 0))

 (fact "after dialog is complete, 'advance-dial
og' command should go back to interact mode"
       (let [dialog-mode-state (state/examine consts/initial consts/single-line-poi)
             dialog-finished-state (state/advance-dialog dialog-mode-state)]
         (:mode dialog-finished-state) => :interact))

 (fact "'advance-dialog' command should trigger next line if dialog has more than one line"
       (let [line-one   (state/examine consts/initial consts/multi-line-poi)
             line-two   (state/advance-dialog line-one)
             line-three (state/advance-dialog line-two)
             dialog-end (state/advance-dialog line-three)]
         (:mode line-one) => :dialog
         (:current-line line-one) => 0

         (:mode line-two) => :dialog
         (:current-line line-two) => 1

         (:mode line-three) => :dialog
         (:current-line line-three) => 2

         (:mode dialog-end) => :interact)))
