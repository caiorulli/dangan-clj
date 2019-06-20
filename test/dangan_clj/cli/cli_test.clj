(ns dangan-clj.cli.cli-test
  (:require [dangan-clj.cli.cli :as cli]
            [midje.sweet :refer [fact facts =>]]
            [clojure.spec.alpha :as s]
            [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.logic.state :as state]
            [dangan-clj.cli.messages :as messages]))

(facts "about cli state"
  (fact "spec validation"
    (s/valid? ::cli/cli nil) => false
    (s/valid? ::cli/cli {}) => false
    (s/valid? ::cli/cli {:mode :lala}) => false
    (s/valid? ::cli/cli {:mode :interact}) => true
    (s/valid? ::cli/cli {:mode :dialog
                         :current-dialog :lala
                         :current-line   0}) => true))

(facts "about cli dialog output"
  (fact "should output formatted dialog"
    (cli/dialog-output {:mode :dialog
                        :current-dialog :knife-dialog
                        :current-line 0} consts/initial)
    =>
    "Giba: That's the knife I used to cut tomatoes."

    (cli/dialog-output {:mode :dialog
                        :current-dialog :describe-giba
                        :current-line 0} consts/initial)
    =>
    "A respectable gentleman")

  (fact "should throw if invalid dialog state?"))

(facts "about cli dialog flow"
  (fact "should be able to enter dialog flow"
    (cli/dialog-mode :schredder-dialog)
    => {:mode :dialog
        :current-dialog :schredder-dialog
        :current-line   0})

  (fact "should be able to advance dialog"
    (-> (cli/dialog-mode :schredder-dialog)
        (cli/next-line consts/initial))
    => {:mode :dialog
        :current-dialog :schredder-dialog
        :current-line   1})

  (fact "should return to interact mode when dialog ends"
    (-> (cli/dialog-mode :knife-dialog)
        (cli/next-line consts/initial))
    => {:mode :interact})

  (fact "should validate for dialog mode?"))

;; Tests for old structure

(facts "about presenting state"
 (fact "on interactive mode, returns nothing"
       (cli/present-state consts/initial "") => nil
       (cli/present-state consts/initial nil) => nil
       (cli/present-state consts/initial {}) => nil)

 (fact "on dialog mode, returns the current speaker and text"
       (cli/present-state consts/dialog-start
                      {:type :examine
                       :target :knife}) => "Giba: That's the knife I used to cut tomatoes."
       (cli/present-state (state/describe consts/initial) {}) => "Giba's hauntingly neat and organized room.")

 (fact "help command should output help text"
       (cli/present-state consts/initial {:type :help}) => messages/help-text))
