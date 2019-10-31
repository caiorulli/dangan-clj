(ns dangan-clj.entrypoint-test
  (:require [dangan-clj.entrypoint :as entrypoint]
            [midje.sweet :refer [=> fact]]
            [dangan-clj.input.example :as example]
            [dangan-clj.cli.cli :as cli]))

(fact "Initial game"
  (let [{:game/keys [output cli content dictionary]} (entrypoint/init example/game example/cli-dict)]
    output => []
    cli => (cli/cli example/game)
    content => example/game
    dictionary => example/cli-dict))

(fact "Input"
  (let [game (entrypoint/init example/game example/cli-dict)
        {:game/keys [output cli content dictionary]} (entrypoint/exec "describe" game)]
    output => ["Rodrigo laid dead on his bed, pale and cold."]
    cli => {:current-dialog :describe-rodrigos-room
            :current-line   0
            :effects        []
            :mode           :dialog
            :player         {:clues #{} :current-scene :rodrigos-room}}
    content => example/game
    dictionary => example/cli-dict))
