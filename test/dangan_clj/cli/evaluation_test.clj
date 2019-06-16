(ns dangan-clj.cli.evaluation-test
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.consts :as consts]
            [midje.sweet :refer [=> fact]]
            [dangan-clj.logic.state :as state]))

(fact "examine command should yield same result from examine fn"
  (cli/evaluate-command consts/initial {:type :examine
                                    :target :knife}) => consts/dialog-start
  (cli/evaluate-command consts/initial {:type :describe}) => (state/describe consts/initial))

(fact
 "on dialog mode, anything should trigger dialog advance"
 (let [after-dialog-state (state/advance-dialog consts/dialog-start)
       evaluate #(cli/evaluate-command consts/dialog-start %)]
   (evaluate "")  => after-dialog-state
   (evaluate nil) => after-dialog-state
   (evaluate {})  => after-dialog-state))

(fact
 "certain commands should not trigger state changes in interact mode"
 (let [evaluate #(cli/evaluate-command consts/initial %)]
   (evaluate nil) => consts/initial
   (evaluate "") => consts/initial
   (evaluate {:type :help}) => consts/initial))

(fact
 "word enter should trigger navigation"
 (let [evaluate #(cli/evaluate-command consts/initial %)]
   (evaluate "") => consts/initial
   (evaluate "enter") => consts/initial
   (evaluate {:type :navigate
              :target :laundry}) => consts/entered-scene-two))
