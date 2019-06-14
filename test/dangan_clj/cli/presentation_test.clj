(ns dangan-clj.cli.presentation-test
  (:require [dangan-clj.cli.cli :refer [present-state]]
            [dangan-clj.cli.messages :refer [help-text]]
            [dangan-clj.input.consts :as consts]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about presenting state"
 (fact "on interactive mode, returns nothing"
       (present-state consts/initial "") => nil
       (present-state consts/initial nil) => nil
       (present-state consts/initial {}) => nil)

 (fact "on dialog mode, returns the current speaker and text"
       (present-state consts/dialog-start
                      consts/start-dialog-command) => consts/formatted-dialog-line)

 (fact "look command should output scene description"
       (present-state consts/initial {:type :describe}) => consts/scene-description)

 (fact "help command should output help text"
       (present-state consts/initial {:type :help}) => help-text))
