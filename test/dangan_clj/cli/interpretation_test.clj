(ns dangan-clj.cli.interpretation-test
  (:require [midje.sweet :refer [fact =>]]
            [dangan-clj.cli :as cli]))

(fact
 "empty or invalid commands should be interpreted to nil"
 (cli/interpret "") => nil
 (cli/interpret "lalala") => nil)
