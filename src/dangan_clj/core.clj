(ns dangan-clj.core
  (:gen-class)
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.input.example :as example]
            [dangan-clj.logic.game :as game]
            [dangan-clj.logic.state :as state]))

(defn- game-loop [state cli-dict]
  (print (cli/make-prompt state))
  (flush)
  (let [command-string (read-line)
        command (cli/interpret cli-dict command-string)
        next-state (cli/evaluate-command state command)
        command-output (cli/present-state next-state command)]
    (when command-output
      (println command-output))
    (recur next-state cli-dict)))

(defn -main
  [& _]
  (let [game-valid? (game/valid? example/game)]
    (if (not game-valid?)
      (println (game/explain example/game))
      (let [state (state/make-initial-state example/game)]
        (println messages/welcome-text)
        (cli/present-state state "")
        (game-loop state example/cli-dict)))))
