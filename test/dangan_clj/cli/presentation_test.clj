(ns dangan-clj.cli.presentation-test
  (:require [dangan-clj.cli.cli :refer [present-state]]
            [dangan-clj.cli.messages :refer [help-text]]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.states :as states]
            [midje.sweet :refer [=> fact facts]]))

(facts
 "about presenting state"
 (fact "on interactive mode, returns nothing"
       (present-state states/initial "") => nil
       (present-state states/initial nil) => nil
       (present-state states/initial {}) => nil)

 (fact "on dialog mode, returns the current speaker and text"
       (present-state states/dialog-start
                      consts/start-dialog-command) => consts/formatted-dialog-line)

 (fact "look command should output scene description"
       (present-state states/initial {:type :describe}) => consts/scene-description)

 (fact "help command should output help text"
       (present-state states/initial {:type :help}) => help-text))
