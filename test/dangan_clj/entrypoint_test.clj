(ns dangan-clj.entrypoint-test
  (:require [dangan-clj.entrypoint :as entrypoint]
            [midje.sweet :refer [=> fact]]
            [dangan-clj.input.example :as example]
            [dangan-clj.cli.cli :as cli]))

(fact "Initial game"
  (let [{:keys [output cli]} (entrypoint/init example/game example/cli-dict)]
    output => []
    cli => (cli/cli example/game)))

(fact "Input"
  (let [game (entrypoint/init example/game example/cli-dict)
        {:keys [output cli]} (entrypoint/exec "describe" example/game example/cli-dict game)]
    output => ["Rodrigo laid dead on his bed, pale and cold."]
    cli => {:current-dialog :describe-rodrigos-room
            :current-line   0
            :effects        []
            :mode           :dialog
            :player         {:clues #{} :current-scene :rodrigos-room}}))
