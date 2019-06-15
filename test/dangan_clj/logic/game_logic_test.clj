(ns dangan-clj.logic.game-logic-test
  (:require [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.game-logic :as logic]
            [midje.sweet :refer [=> fact facts]]))

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
       (let [already-interacted-state (logic/examine consts/initial consts/single-line-poi)]
         (-> (logic/examine already-interacted-state consts/single-line-poi)
             :player
             :clues) => [test-game/clue-1]))

 (fact "player should add clues by interacting with poi"
       (-> (logic/examine consts/initial consts/single-line-poi)
           :player
           :clues) => [test-game/clue-1])

 (fact "player examining non-existing poi should not return different player"
       (logic/examine consts/initial "balloon") => consts/initial))

(facts
 "about dialog mode"
 (fact "interaction should trigger dialog mode"
       (let [dialog-mode-state (logic/examine consts/initial consts/single-line-poi)]
         (:mode dialog-mode-state) => :dialog
         (:current-line dialog-mode-state) => 0))

 (fact "after dialog is complete, 'advance-dial
og' command should go back to interact mode"
       (let [dialog-mode-state (logic/examine consts/initial consts/single-line-poi)
             dialog-finished-state (logic/advance-dialog dialog-mode-state)]
         (:mode dialog-finished-state) => :interact))

 (fact "'advance-dialog' command should trigger next line if dialog has more than one line"
       (let [line-one   (logic/examine consts/initial consts/multi-line-poi)
             line-two   (logic/advance-dialog line-one)
             line-three (logic/advance-dialog line-two)
             dialog-end (logic/advance-dialog line-three)]
         (:mode line-one) => :dialog
         (:current-line line-one) => 0

         (:mode line-two) => :dialog
         (:current-line line-two) => 1

         (:mode line-three) => :dialog
         (:current-line line-three) => 2

         (:mode dialog-end) => :interact)))
