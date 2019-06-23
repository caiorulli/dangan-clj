(ns dangan-clj.cli.cli-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.player :as player]
            [midje.sweet :refer [=> fact facts]]))

(facts "about cli state"
       (fact "spec validation"
             (s/valid? ::cli/cli nil) => false
             (s/valid? ::cli/cli {}) => false
             (s/valid? ::cli/cli {:player (player/player
                                          test-game/test-game)}) => false
             (s/valid? ::cli/cli {:mode :lala}) => false
             (s/valid? ::cli/cli {:mode :interact
                                  :player (player/player
                                          test-game/test-game)}) => true
             (s/valid? ::cli/cli {:mode :dialog
                                  :current-dialog :lala
                                  :current-line   0
                                  :player (player/player
                                          test-game/test-game)}) => true)

       (fact "cli fn should make valid cli"
         (s/valid? ::cli/cli (cli/cli test-game/test-game))))

(facts "about cli output"
       (fact "should output formatted dialog"
             (cli/output {:mode :dialog
                          :current-dialog :knife-dialog
                          :current-line 0} test-game/test-game {})
             =>
             "Giba: That's the knife I used to cut tomatoes."

             (cli/output {:mode :dialog
                          :current-dialog :describe-giba
                          :current-line 0} test-game/test-game {})
             =>
             "A respectable gentleman"

             (cli/output {:mode :interact} test-game/test-game {})
             => nil

             (cli/output {:mode :interact} test-game/test-game {:type :help})
             => messages/help-text))

(facts "about cli dialog flow"
       (fact "should be able to enter dialog flow"
             (cli/dialog-mode consts/initial-cli :schredder-dialog)
             => {:mode :dialog
                 :current-dialog :schredder-dialog
                 :current-line   0
                 :player consts/initial-player})

       (fact "should be able to advance dialog"
         (-> consts/initial-cli
          (cli/dialog-mode :schredder-dialog)
          (cli/next-line test-game/test-game))
             => {:mode :dialog
                 :current-dialog :schredder-dialog
                 :current-line   1
                 :player consts/initial-player})

       (fact "should return to interact mode when dialog ends"
         (-> consts/initial-cli
          (cli/dialog-mode :knife-dialog)
          (cli/next-line test-game/test-game))
         => {:mode :interact
             :player consts/initial-player}))

(facts "about simple text modes"
  (fact "list clues mode"
    (cli/list-clues-mode consts/initial-cli)
    => {:mode :interact
        :player consts/initial-player
        :simple-text :list-clues}))

(facts "about prompt generation"
 (fact "returns scene name prompt"
   (cli/prompt (cli/interact-mode consts/initial-cli)
                   test-game/test-game) => "(Giba's Room) > ")

 (fact "on dialog mode, should display three dots"
       (cli/prompt (cli/dialog-mode consts/initial-cli :knife-dialog)
                   test-game/test-game) => "..."))
