(ns dangan-clj.cli.presentation-test
  (:require [dangan-clj.cli.cli :refer [present-state]]
            [dangan-clj.cli.messages :refer [help-text]]
            [dangan-clj.input.consts :as consts]
            [midje.sweet :refer [=> fact facts]]
            [dangan-clj.logic.state :as state]))

(facts
 "about presenting state"
 (fact "on interactive mode, returns nothing"
       (present-state consts/initial "") => nil
       (present-state consts/initial nil) => nil
       (present-state consts/initial {}) => nil)

 (fact "on dialog mode, returns the current speaker and text"
       (present-state consts/dialog-start
                      {:type :examine
                       :target :knife}) => "Giba: That's the knife I used to cut tomatoes."
       (present-state (state/describe consts/initial) {}) => "Giba's hauntingly neat and organized room.")

 (fact "help command should output help text"
       (present-state consts/initial {:type :help}) => help-text))
