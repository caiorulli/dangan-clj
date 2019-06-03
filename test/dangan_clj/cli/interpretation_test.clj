(ns dangan-clj.cli.interpretation-test
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.consts :as consts]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about interpretation"
 (fact
  "empty or invalid commands should be interpreted to nil"
  (cli/interpret consts/initial "") => nil
  (cli/interpret consts/initial "lalala") => nil)

 (fact
  "describe synonims should be interpreted as describe commands"
  (cli/interpret consts/initial "describe") => {:type :describe})

 (fact
  "help synonims should be interpreted as help commands"
  (cli/interpret consts/initial "help") => {:type :help})

 (fact
  "examine synonims should be interpreted as examine commands"
  (let [interpret #(cli/interpret consts/initial %)
        examine-schredder-command {:type :examine
                                 :target :schredder}]
    (interpret "examine schredder") => examine-schredder-command
    (interpret "examine black box")  => examine-schredder-command
    (interpret "examine BOX") => examine-schredder-command))

 (fact
  "enter synonims should be interpreted as navigate commands"
  (let [interpret #(cli/interpret consts/initial %)
        enter-laundry-command {:type :navigate
                            :target :laundry}
        enter-room-command {:type :navigate
                            :target :gibas-room}]
    (interpret "enter Laundry") => enter-laundry-command
    (interpret "enter laundry") => enter-laundry-command
    (interpret "enter laundry area") => enter-laundry-command
    (interpret "enter Giba's Room") => enter-room-command
    (interpret "enter room") => enter-room-command)))
