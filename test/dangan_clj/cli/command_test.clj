(ns dangan-clj.cli.command-test
  (:require  [midje.sweet :refer [fact facts =>]]
             [clojure.spec.alpha :as s]
             [dangan-clj.input.test-game :as test-game]
             [dangan-clj.cli.command :as command]))

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

(facts
 "about interpretation"
 (fact
  "empty or invalid commands should be interpreted to nil"
  (make-command "") => nil
  (make-command "lalala") => nil)

 (fact
  "describe synonims should be interpreted as describe commands"
  (make-command "describe") => {:type :describe})

 (fact
  "help synonims should be interpreted as help commands"
  (make-command "help") => {:type :help})

 (fact
  "examine synonims should be interpreted as examine commands"
  (let [examine-schredder-command {:type :examine
                                   :target :schredder}]
    (make-command "examine schredder") => examine-schredder-command
    (make-command "examine black box")  => examine-schredder-command
    (make-command "examine BOX") => examine-schredder-command))

 (fact
  "enter synonims should be interpreted as navigate commands"
  (let [enter-laundry-command {:type :navigate
                               :target :laundry}
        enter-room-command {:type :navigate
                            :target :gibas-room}]
    (make-command "enter Laundry") => enter-laundry-command
    (make-command "enter laundry") => enter-laundry-command
    (make-command "enter laundry area") => enter-laundry-command
    (make-command "enter Giba's Room") => enter-room-command
    (make-command "enter room") => enter-room-command)))

