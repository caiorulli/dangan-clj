(ns dangan-clj.cli.evaluation-test
  (:require [dangan-clj.cli.cli :refer [evaluate-command]]
            [dangan-clj.input.consts :as consts]
            [midje.sweet :refer [=> fact]]
            [dangan-clj.logic.state :as state]))

(fact
 "examine command should yield same result from examine fn"
 (evaluate-command consts/initial consts/start-dialog-command) => consts/dialog-start)

(fact
 "on dialog mode, anything should trigger dialog advance"
 (let [after-dialog-state (state/advance-dialog consts/dialog-start)
       evaluate #(evaluate-command consts/dialog-start %)]
   (evaluate "")  => after-dialog-state
   (evaluate nil) => after-dialog-state
   (evaluate {})  => after-dialog-state))

(fact
 "certain commands should not trigger state changes in interact mode"
 (let [evaluate #(evaluate-command consts/initial %)]
   (evaluate nil) => consts/initial
   (evaluate "") => consts/initial
   (evaluate {:type :describe}) => consts/initial
   (evaluate {:type :help}) => consts/initial))

(fact
 "word enter should trigger navigation"
 (let [evaluate #(evaluate-command consts/initial %)]
   (evaluate "") => consts/initial
   (evaluate "enter") => consts/initial
   (evaluate {:type :navigate
              :target :laundry}) => consts/entered-scene-two))
