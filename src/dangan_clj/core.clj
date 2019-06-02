(ns dangan-clj.core
  (:gen-class)
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.game-logic :refer [make-initial-state]]
            [dangan-clj.input.example :as example]))

(defn- game-loop [state]
  (print (cli/make-prompt state))
  (flush)
  (let [command-string (read-line)
        command (cli/interpret state command-string)
        next-state (cli/evaluate-command state command)
        command-output (cli/present-state next-state command)]
    (when command-output
      (println command-output))
    (recur next-state)))

(defn -main
  [& _]
  (println messages/welcome-text)
  (let [state (make-initial-state example/game example/cli-dict)]
    (cli/present-state state "")
    (game-loop state)))
