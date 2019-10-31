(ns dangan-clj.entrypoint-test
  (:require [dangan-clj.entrypoint :as entrypoint]
            [midje.sweet :refer [=> fact facts]]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.cli.cli :as cli]))

(fact "Initial game"
  (let [{:game/keys [log cli content dictionary]} (entrypoint/init test-game/test-game test-game/cli-dict)]
    log => []
    cli => (cli/cli test-game/test-game)
    content => test-game/test-game
    dictionary => test-game/cli-dict))

(fact "Input"
  (let [game (entrypoint/init test-game/test-game test-game/cli-dict)
        {:game/keys [log cli content dictionary]} (entrypoint/exec "describe" game)]
    log => [#:line {:speaker nil
                    :text    "Giba's hauntingly neat and organized room."}]
    cli => {:current-dialog :describe-gibas-room
            :current-line   0
            :effects        []
            :mode           :dialog
            :player         {:clues #{} :current-scene :gibas-room}}
    content => test-game/test-game
    dictionary => test-game/cli-dict))

(facts "Line query"
  (fact "No current-dialog and current-line properties"
    (let [game {:game/cli     {}
                :game/content {:dialogs    {:lala [[:dr-gori "Pare!"]
                                                   [:dr-gori "Não deixarei..."]]}
                               :characters {:dr-gori {:display-name "Dr. Gori"}}}}]
      (entrypoint/line game) => nil))

  (fact "Existing line"
    (let [game {:game/cli     {:current-dialog :lala
                               :current-line   1}
                :game/content {:dialogs    {:lala [[:dr-gori "Pare!"]
                                                   [:dr-gori "Não deixarei..."]]}
                               :characters {:dr-gori {:display-name "Dr. Gori"}}}}]
      (entrypoint/line game) => {:line/speaker "Dr. Gori"
                                 :line/text    "Não deixarei..."})))
