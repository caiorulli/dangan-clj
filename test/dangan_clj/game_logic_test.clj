(ns dangan-clj.game-logic-test
  (:require [dangan-clj.game
             [consts :as consts]
             [example :refer [clue-1]]
             [states :as states]]
            [dangan-clj.game-logic :as logic]
            [midje.sweet :refer [=> fact facts]]))

(def initial-state states/initial)

(facts
 "about initial state"
 (fact "should have correct initial configuration"
       (:mode initial-state) => :interact))

(facts
 "about the player interaction"
 (fact "player should start clueless"
       (-> initial-state
           :player
           :clues) => #{})

 (fact "player should not be able to add same clue twice"
       (let [already-interacted-state (logic/examine initial-state consts/single-line-poi)]
         (-> (logic/examine already-interacted-state consts/single-line-poi)
             :player
             :clues) => #{clue-1}))

 (fact "player should add clues by interacting with poi"
       (-> (logic/examine initial-state consts/single-line-poi)
           :player
           :clues) => #{clue-1})

 (fact "player examining non-existing poi should not return different player"
       (logic/examine initial-state "balloon") => initial-state))

(facts
 "about dialog mode"
 (fact "interaction should trigger dialog mode"
       (let [dialog-mode-state (logic/examine initial-state consts/single-line-poi)]
         (:mode dialog-mode-state) => :dialog
         (:line dialog-mode-state) => 0))

 (fact "after dialog is complete, 'advance-dial
og' command should go back to interact mode"
       (let [dialog-mode-state (logic/examine initial-state consts/single-line-poi)
             dialog-finished-state (logic/advance-dialog dialog-mode-state)]
         (:mode dialog-finished-state) => :interact))

 (fact "'advance-dialog' command should trigger next line if dialog has more than one line"
       (let [line-one   (logic/examine initial-state consts/multi-line-poi)
             line-two   (logic/advance-dialog line-one)
             line-three (logic/advance-dialog line-two)
             dialog-end (logic/advance-dialog line-three)]
         (:mode line-one) => :dialog
         (:line line-one) => 0

         (:mode line-two) => :dialog
         (:line line-two) => 1

         (:mode line-three) => :dialog
         (:line line-three) => 2

         (:mode dialog-end) => :interact)))
