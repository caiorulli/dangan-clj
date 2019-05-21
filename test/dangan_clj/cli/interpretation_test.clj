(ns dangan-clj.cli.interpretation-test
  (:require [midje.sweet :refer [fact =>]]
            [dangan-clj.cli :as cli]))

(fact
 "empty or invalid commands should be interpreted to nil"
 (cli/interpret "") => nil
 (cli/interpret "lalala") => nil)

(fact
 "describe synonims should be interpreted as describe commands"
 (cli/interpret "describe") => {:type :describe})

(fact
 "help synonims should be interpreted as help commands"
 (cli/interpret "help") => {:type :help})

(fact
 "examine synonims should be interpreted as examine commands"
 (cli/interpret "examine rodrigo") => {:type   :examine
                                       :target "rodrigo"})
