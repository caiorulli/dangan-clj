(ns dangan-clj.cli.evaluation-test
  (:require [dangan-clj
             [cli :refer [evaluate-command]]
             [game-logic :refer [advance-dialog]]]
            [dangan-clj.game
             [consts :as consts]
             [states :as states]]
            [midje.sweet :refer [=> fact facts]]))

(fact
 "should yield same result from examine fn"
 (evaluate-command states/initial consts/start-dialog-command) => states/dialog-start)

(fact
 "on dialog mode, any command should trigger dialog advance"
 (let [after-dialog-state (advance-dialog states/dialog-start)]
   (evaluate-command states/dialog-start "") => after-dialog-state))

(fact
 "certain commands should not trigger state changes in interact mode"
 (let [evaluate #(evaluate-command states/initial %)]
   (evaluate "describe") => states/initial
   (evaluate "help") => states/initial
   (evaluate "knife") => states/initial
   (evaluate "") => states/initial))

(fact
 "word enter should trigger navigation"
 (let [evaluate #(evaluate-command states/initial %)]
   (evaluate "") => states/initial
   (evaluate "enter") => states/initial))
