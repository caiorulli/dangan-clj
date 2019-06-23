(ns dangan-clj.cli.command-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.command :as command]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [midje.sweet :refer [=> fact facts]]
            [dangan-clj.cli.messages :as messages]))

(facts "about command validation"
       (fact "should always have type"
             (s/valid? ::command/command nil) => false
             (s/valid? ::command/command {}) => false
             (s/valid? ::command/command {:type :lala}) => false

             (s/valid? ::command/command {:type :describe}) => true
             (s/valid? ::command/command {:type :examine}) => true
             (s/valid? ::command/command {:type :help}) => true)

       (fact "may have a target id"
             (s/valid? ::command/command {:type :examine
                                          :target :spectreman}) => true
             (s/valid? ::command/command {:type :examine
                                          :target "conquista"}) => false))

(def make-command #(command/make % test-game/cli-dict))

(facts "about interpretation"
       (fact "empty or invalid commands should be interpreted to nil"
             (make-command "") => nil
             (make-command "lalala") => nil)

       (fact "describe synonims should be interpreted as describe commands"
             (make-command "describe") => {:type :describe})

       (fact "help synonims should be interpreted as help commands"
             (make-command "help") => {:type :help})

       (fact "talk to synonyms should be interpreted as talk commands"
             (make-command "talk to giba") => {:type :talk
                                               :target :giba})

       (fact "examine synonims should be interpreted as examine commands"
             (let [examine-schredder-command {:type :examine
                                              :target :schredder}]
               (make-command "examine schredder") => examine-schredder-command
               (make-command "examine black box")  => examine-schredder-command
               (make-command "examine BOX") => examine-schredder-command))

       (fact "go to synonims should be interpreted as navigate commands"
             (let [enter-laundry-command {:type :navigate
                                          :target :laundry}
                   enter-room-command {:type :navigate
                                       :target :gibas-room}]
               (make-command "go to Laundry") => enter-laundry-command
               (make-command "go to laundry") => enter-laundry-command
               (make-command "go to laundry area") => enter-laundry-command
               (make-command "go to Giba's Room") => enter-room-command
               (make-command "go to room") => enter-room-command))

       (fact "list clues should be interpreted as list clues command"
             (make-command "list clues") => {:type :list-clues}
             (make-command "LisT Clue") => {:type :list-clues}))

(facts "about command cli evaluation"
       (fact "examine should trigger dialog mode"
             (command/evaluate-cli {:type :examine
                                    :target :knife}
                                   consts/initial-cli
                                   test-game/test-game)
             => (cli/examine consts/initial-cli test-game/test-game :knife))

       (fact "talk command should trigger dialog mode"
             (command/evaluate-cli {:type :talk
                                    :target :giba}
                                   consts/initial-cli
                                   test-game/test-game)
             => (cli/dialog-mode consts/initial-cli :giba-talk))

       (fact "talk command should not trigger dialog mode if character not present"
             (command/evaluate-cli {:type :talk
                                    :target :rodrigo}
                                   consts/initial-cli
                                   test-game/test-game)
             => consts/initial-cli)

       (fact "describe command should trigger dialog mode"
             (command/evaluate-cli {:type :describe}
                                   consts/initial-cli
                                   test-game/test-game)
             => (cli/dialog-mode consts/initial-cli :describe-gibas-room))

       (fact "if in dialog mode, any command will trigger next line"
             (let [dialog-mode (cli/dialog-mode consts/initial-cli :schredder-dialog)]
               (command/evaluate-cli {:type :describe}
                                     dialog-mode
                                     test-game/test-game)
               => (cli/next-line dialog-mode test-game/test-game)))

       (fact "if in interact mode, any other command will return interact mode"
             (command/evaluate-cli {:type :lala}
                                   consts/initial-cli
                                   test-game/test-game)
             => consts/initial-cli)

       (fact "word enter should trigger navigation"
             (command/evaluate-cli {:type :navigate
                                    :target :laundry}
                                   consts/initial-cli
                                   test-game/test-game)
             => {:mode :interact
                 :player consts/entered-scene-two})

       (fact "list clues will trigger simple text interact mode"
             (command/evaluate-cli {:type :list-clues}
                                   consts/initial-cli
                                   test-game/test-game)
             => (cli/simple-text-mode consts/initial-cli :list-clues))

       (fact "help command will trigger simple text mode"
             (command/evaluate-cli {:type :help}
                                   consts/initial-cli
                                   test-game/test-game)
             => (cli/simple-text-mode consts/initial-cli :help)))
