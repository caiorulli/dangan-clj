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
    (s/valid? ::cli/cli {:mode   :interact
                         :player (player/player
                                  test-game/test-game)}) => true
    (s/valid? ::cli/cli {:mode           :dialog
                         :current-dialog :lala
                         :current-line   0
                         :player         (player/player
                                          test-game/test-game)}) => true)

  (fact "cli fn should make valid cli"
    (s/valid? ::cli/cli (cli/cli test-game/test-game))))

(facts "about cli output"
  (fact "should output formatted dialog"
    (cli/output {:mode           :dialog
                 :current-dialog :knife-dialog
                 :current-line   0} test-game/test-game)
    =>
    "Giba: That's the knife I used to cut tomatoes."

    (cli/output {:mode           :dialog
                 :current-dialog :describe-giba
                 :current-line   0} test-game/test-game)
    =>
    "A respectable gentleman"

    (cli/output {:mode :interact} test-game/test-game)
    => nil)

  (fact "dialog may have effects to apply at the end of the dialog"
    (cli/output {:mode           :dialog
                 :current-dialog :knife-dialog
                 :current-line   1
                 :effects        ["Obtained clue: Bloody Knife"]} test-game/test-game)
    =>
    "** Obtained clue: Bloody Knife **"))

(facts "about cli dialog flow"
  (fact "should be able to enter dialog flow"
    (cli/dialog-mode consts/initial-cli :schredder-dialog)
    => {:mode           :dialog
        :current-dialog :schredder-dialog
        :current-line   0
        :player         consts/initial-player
        :effects        []})

  (fact "should be able to advance dialog"
    (-> consts/initial-cli
        (cli/dialog-mode :schredder-dialog)
        (cli/next-line test-game/test-game))
    => {:mode           :dialog
        :current-dialog :schredder-dialog
        :current-line   1
        :player         consts/initial-player
        :effects        []})

  (fact "should return to interact mode when dialog ends"
    (-> consts/initial-cli
        (cli/dialog-mode :knife-dialog)
        (cli/next-line test-game/test-game))
    => {:mode   :interact
        :player consts/initial-player})

  (fact "should not return to interact mode when dialog ends if has effects"
    (-> consts/initial-cli
        (cli/dialog-mode :knife-dialog "i am effect")
        (cli/next-line test-game/test-game))
    => {:mode           :dialog
        :current-dialog :knife-dialog
        :current-line   1
        :player         consts/initial-player
        :effects        ["i am effect"]})

  (fact "should not fail when returning to interact mode"
    (-> consts/initial-cli
        (cli/dialog-mode :knife-dialog "i am effect")
        (cli/next-line test-game/test-game)
        (cli/next-line test-game/test-game))
    => {:mode   :interact
        :player consts/initial-player}))

(facts "about simple text modes"
  (fact "simple-text-mode should add simple-text entry"
    (cli/simple-text-mode consts/initial-cli :list-clues)
    => {:mode        :interact
        :player      consts/initial-player
        :simple-text :list-clues})

  (fact "empty clues list-clues text type output"
    (let [cli (cli/simple-text-mode consts/initial-cli :list-clues)]
      (cli/output cli test-game/test-game)
      => "Clues in possession:\n\nYou don't have any clues yet.\n"))

  (fact "with clues list-clues text type output"
    (let [cli (-> consts/initial-cli
                  (update :player #(player/with-clue % :bloody-knife))
                  (update :player #(player/with-clue % :schredder))
                  (cli/simple-text-mode :list-clues))]
      (cli/output cli test-game/test-game)
      => "Clues in possession:\n\nBloody knife - He says it's tomato sauce, but I don't buy it.\nSchredder - Self-explanatory.\n"))

  (fact "help text type output"
    (let [cli (cli/simple-text-mode consts/initial-cli :help)]
      (cli/output cli test-game/test-game)
      => (messages/help-text cli test-game/test-game)))

  (fact "interact mode should clean up simple-text entry"
    (-> consts/initial-cli
        (cli/simple-text-mode :help)
        cli/interact-mode
        :simple-text)
    => nil))

(facts "about prompt generation"
  (fact "returns scene name prompt"
    (cli/prompt (cli/interact-mode consts/initial-cli)
                test-game/test-game) => "(Giba's Room) > ")

  (fact "on dialog mode, should display three dots"
    (cli/prompt (cli/dialog-mode consts/initial-cli :knife-dialog)
                test-game/test-game) => "..."))

(facts "about cli domain fns"
  (fact "examine should trigger dialog mode"
    (let [result (cli/examine consts/initial-cli test-game/test-game :knife)]
      (:mode result) => :dialog
      (:current-dialog result) => :knife-dialog
      (:current-line result) => 0))

  (fact "examine should add clue to player if exists in poi"
    (-> (cli/examine consts/initial-cli test-game/test-game :knife)
        :player :clues)
    => #{:bloody-knife})

  (fact "examine should add clue effect to cli if clue exists"
    (-> (cli/examine consts/initial-cli test-game/test-game :knife)
        :effects)
    => ["Obtained clue: Bloody knife"])

  (fact "examine should not add clue to player if it does not exist in poi"
    (-> (cli/examine consts/initial-cli test-game/test-game :schredder)
        :player :clues)
    => #{})

  (fact "examine should not trigger dialog mode if target does not exist in scene"
    (cli/examine consts/initial-cli test-game/test-game :washing-machine)
    => consts/initial-cli)

  (fact "examine should work for characters too"
    (cli/examine consts/initial-cli test-game/test-game :giba)
    => (cli/dialog-mode consts/initial-cli :describe-giba))

  (fact "examine should not work for characters that are not present"
    (cli/examine consts/initial-cli test-game/test-game :rodrigo)
    => consts/initial-cli))
