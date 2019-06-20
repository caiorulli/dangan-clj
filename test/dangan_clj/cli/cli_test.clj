(ns dangan-clj.cli.cli-test
  (:require [dangan-clj.cli.cli :as cli]
            [midje.sweet :refer [fact facts =>]]
            [clojure.spec.alpha :as s]
            [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.logic.state :as state]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.input.test-game :as test-game]))

(facts "about cli state"
  (fact "spec validation"
    (s/valid? ::cli/cli nil) => false
    (s/valid? ::cli/cli {}) => false
    (s/valid? ::cli/cli {:mode :lala}) => false
    (s/valid? ::cli/cli {:mode :interact}) => true
    (s/valid? ::cli/cli {:mode :dialog
                         :current-dialog :lala
                         :current-line   0}) => true))

(facts "about cli output"
  (fact "should output formatted dialog"
    (cli/output {:mode :dialog
                 :current-dialog :knife-dialog
                 :current-line 0} consts/initial {})
    =>
    "Giba: That's the knife I used to cut tomatoes."

    (cli/output {:mode :dialog
                 :current-dialog :describe-giba
                 :current-line 0} consts/initial {})
    =>
    "A respectable gentleman"

    (cli/output {:mode :interact} consts/initial {})
    => nil

    (cli/output {:mode :interact} consts/initial {:type :help})
    => messages/help-text))

(facts "about cli dialog flow"
  (fact "should be able to enter dialog flow"
    (cli/dialog-mode :schredder-dialog)
    => {:mode :dialog
        :current-dialog :schredder-dialog
        :current-line   0})

  (fact "should be able to advance dialog"
    (-> (cli/dialog-mode :schredder-dialog)
        (cli/next-line test-game/test-game))
    => {:mode :dialog
        :current-dialog :schredder-dialog
        :current-line   1})

  (fact "should return to interact mode when dialog ends"
    (-> (cli/dialog-mode :knife-dialog)
        (cli/next-line test-game/test-game))
    => {:mode :interact})

  (fact "should validate for dialog mode?"))

(facts
 "about prompt generation"
 (fact "returns scene name prompt"
       (cli/prompt cli/interact-mode consts/initial) => consts/scene-prompt)

 (fact "on dialog mode, should display three dots"
       (cli/prompt (cli/dialog-mode :knife-dialog) consts/initial) => "..."))
