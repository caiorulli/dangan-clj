(ns dangan-clj.cli.cli-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.cli.cli :as cli]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [midje.sweet :refer [=> fact facts]]))

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

  (fact "interact mode should clean up simple-text entry"
    (-> consts/initial-cli
        (cli/simple-text-mode :help)
        cli/interact-mode
        :simple-text)
    => nil))

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
