(ns dangan-clj.core
  (:gen-class)
  (:require [dangan-clj
             [cli :as cli]
             [game-logic :refer [make-initial-state]]]
            [dangan-clj.game.example :as example]))

(def welcome-text
  (str "\nWelcome to the demo game for dangan-clj.\n"
       "In case of need, type \"help\".\n"))

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
  [& args]
  (println welcome-text)
  (let [state (make-initial-state example/arandu-game)]
    (cli/present-state state "")
    (game-loop state)))
