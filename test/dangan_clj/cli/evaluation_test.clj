(ns dangan-clj.cli.evaluation-test
  (:require [dangan-clj.cli.cli :refer [evaluate-command]]
            [dangan-clj.game-logic :refer [advance-dialog]]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.states :as states]
            [midje.sweet :refer [=> fact facts]]))

(fact
 "examine command should yield same result from examine fn"
 (evaluate-command states/initial consts/start-dialog-command) => states/dialog-start)

(fact
 "on dialog mode, anything should trigger dialog advance"
 (let [after-dialog-state (advance-dialog states/dialog-start)
       evaluate #(evaluate-command states/dialog-start %)]
   (evaluate "")  => after-dialog-state
   (evaluate nil) => after-dialog-state
   (evaluate {})  => after-dialog-state))

(fact
 "certain commands should not trigger state changes in interact mode"
 (let [evaluate #(evaluate-command states/initial %)]
   (evaluate nil) => states/initial
   (evaluate "") => states/initial
   (evaluate {:type :describe}) => states/initial
   (evaluate {:type :help}) => states/initial))

(fact
 "word enter should trigger navigation"
 (let [evaluate #(evaluate-command states/initial %)]
   (evaluate "") => states/initial
   (evaluate "enter") => states/initial
   (evaluate {:type :navigate
              :target :pool}) => states/entered-scene-two))
