(ns dangan-clj.cli.command-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.cli.command :as command]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.state :as state]
            [midje.sweet :refer [=> fact facts]]))

(facts "about command validation"
       (fact "should always have type"
             (s/valid? ::command/command nil) => false
             (s/valid? ::command/command {}) => false
             (s/valid? ::command/command {:type :lala}) => false

             (s/valid? ::command/command {:type :describe}) => true
             (s/valid? ::command/command {:type :examine}) => true
             (s/valid? ::command/command {:type :help}) => true)

       (fact "may have a target id"
             (s/valid? ::command/command {:type :examine
                                          :target :spectreman}) => true
             (s/valid? ::command/command {:type :examine
                                          :target "conquista"}) => false))

(def make-command #(command/make % test-game/cli-dict))

(facts "about interpretation"
 (fact "empty or invalid commands should be interpreted to nil"
  (make-command "") => nil
  (make-command "lalala") => nil)

 (fact "describe synonims should be interpreted as describe commands"
  (make-command "describe") => {:type :describe})

 (fact "help synonims should be interpreted as help commands"
   (make-command "help") => {:type :help})

 (fact "talk to synonyms should be interpreted as talk commands"
   (make-command "talk to giba") => {:type :talk
                                     :target :giba})

 (fact
  "examine synonims should be interpreted as examine commands"
  (let [examine-schredder-command {:type :examine
                                   :target :schredder}]
    (make-command "examine schredder") => examine-schredder-command
    (make-command "examine black box")  => examine-schredder-command
    (make-command "examine BOX") => examine-schredder-command))

 (fact
  "go to synonims should be interpreted as navigate commands"
  (let [enter-laundry-command {:type :navigate
                               :target :laundry}
        enter-room-command {:type :navigate
                            :target :gibas-room}]
    (make-command "go to Laundry") => enter-laundry-command
    (make-command "go to laundry") => enter-laundry-command
    (make-command "go to laundry area") => enter-laundry-command
    (make-command "go to Giba's Room") => enter-room-command
    (make-command "go to room") => enter-room-command)))

(def evaluate-init #(command/evaluate % consts/initial))

(facts "about command evaluation"
  (fact "examine command should yield same result from examine fn"
    (evaluate-init {:type :examine
                    :target :knife}) => consts/dialog-start
    (evaluate-init {:type :describe}) => (state/describe consts/initial))

  (fact "on dialog mode, anything should trigger dialog advance"
    (let [after-dialog-state (state/advance-dialog consts/dialog-start)
          evaluate #(command/evaluate % consts/dialog-start)]
      (evaluate "")  => after-dialog-state
      (evaluate nil) => after-dialog-state
      (evaluate {})  => after-dialog-state))

  (fact "certain commands should not trigger state changes in interact mode"
    (let [evaluate #(command/evaluate % consts/initial)]
      (evaluate nil) => consts/initial
      (evaluate "") => consts/initial
      (evaluate {:type :help}) => consts/initial))

  (fact "word enter should trigger navigation"
    (evaluate-init {:type :navigate
                    :target :laundry}) => consts/entered-scene-two)

  (fact ":talk command type should trigger talk-to state fn"
    (evaluate-init {:type :talk
                    :target :giba}) => (state/talk-to consts/initial :giba)))