(ns dangan-clj.cli.interpretation-test
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.test-game :as test-game]
            [midje.sweet :refer [=> fact facts]]))

(def interpret #(cli/interpret test-game/cli-dict %))

(facts
 "about interpretation"
 (fact
  "empty or invalid commands should be interpreted to nil"
  (interpret "") => nil
  (interpret "lalala") => nil)

 (fact
  "describe synonims should be interpreted as describe commands"
  (interpret "describe") => {:type :describe})

 (fact
  "help synonims should be interpreted as help commands"
  (interpret "help") => {:type :help})

 (fact
  "examine synonims should be interpreted as examine commands"
  (let [examine-schredder-command {:type :examine
                                   :target :schredder}]
    (interpret "examine schredder") => examine-schredder-command
    (interpret "examine black box")  => examine-schredder-command
    (interpret "examine BOX") => examine-schredder-command))

 (fact
  "enter synonims should be interpreted as navigate commands"
  (let [enter-laundry-command {:type :navigate
                               :target :laundry}
        enter-room-command {:type :navigate
                            :target :gibas-room}]
    (interpret "enter Laundry") => enter-laundry-command
    (interpret "enter laundry") => enter-laundry-command
    (interpret "enter laundry area") => enter-laundry-command
    (interpret "enter Giba's Room") => enter-room-command
    (interpret "enter room") => enter-room-command)))
