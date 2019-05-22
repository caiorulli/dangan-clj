(ns dangan-clj.cli.interpretation-test
  (:require [midje.sweet :refer [fact =>]]
            [dangan-clj.cli :as cli]
            [dangan-clj.game.states :as states]))

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
 (cli/interpret states/initial "examine rodrigo") => {:type   :examine
                                                      :target :rodrigo})

(fact
 "enter synonims should be interpreted as navigate commands"
 (cli/interpret states/initial "enter Pool") => {:type :navigate
                                                 :target :pool})
