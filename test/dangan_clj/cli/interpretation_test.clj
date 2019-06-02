(ns dangan-clj.cli.interpretation-test
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.states :as states]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about interpretation"
 (fact
  "empty or invalid commands should be interpreted to nil"
  (cli/interpret states/initial "") => nil
  (cli/interpret states/initial "lalala") => nil)

 (fact
  "describe synonims should be interpreted as describe commands"
  (cli/interpret states/initial "describe") => {:type :describe})

 (fact
  "help synonims should be interpreted as help commands"
  (cli/interpret states/initial "help") => {:type :help})

 (fact
  "examine synonims should be interpreted as examine commands"
  (let [interpret #(cli/interpret states/initial %)
        examine-rodrigo-command {:type :examine
                                 :target :rodrigo}]
    (interpret "examine rodrigo") => examine-rodrigo-command
    (interpret "examine batata")  => examine-rodrigo-command
    (interpret "examine RodRigO") => examine-rodrigo-command))

 (fact
  "enter synonims should be interpreted as navigate commands"
  (let [interpret #(cli/interpret states/initial %)
        enter-pool-command {:type :navigate
                            :target :pool}
        enter-room-command {:type :navigate
                            :target :rodrigos-room}]
    (interpret "enter Pool") => enter-pool-command
    (interpret "enter pool") => enter-pool-command
    (interpret "enter pool area") => enter-pool-command
    (interpret "enter Rodrigo's Room") => enter-room-command
    (interpret "enter room") => enter-room-command)))
