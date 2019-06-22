(ns dangan-clj.core
  (:gen-class)
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.command :as command]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.input.example :as example]
            [dangan-clj.logic.game :as game]
            [dangan-clj.logic.state :as state]))

(defn- game-loop [state cli-dict cli game]
  (print (cli/prompt cli game))
  (flush)
  (let [command-string (read-line)
        command (command/make command-string cli-dict)
        next-state (command/evaluate-state command state game)
        next-cli (command/evaluate-cli command cli state game)
        command-output (cli/output next-cli game command)]
    (when command-output
      (println command-output))
    (recur next-state cli-dict next-cli game)))

(defn -main
  [& _]
  (let [game-valid? (game/valid? example/game)]
    (if-not game-valid?
      (println (game/explain example/game))
      (let [state (state/initial-state example/game)
            cli   (cli/cli example/game)]
        (println messages/welcome-text)
        (game-loop state example/cli-dict cli example/game)))))
