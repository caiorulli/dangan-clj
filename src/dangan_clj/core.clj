(ns dangan-clj.core
  (:require [dangan-clj.game-logic :refer [make-initial-state]]
            [dangan-clj.cli        :refer [make-prompt
                                           evaluate-command
                                           present-state]]
            [dangan-clj.game.example :refer [test-scene]])
  (:gen-class))

(def welcome-text
  (str "\nWelcome to the demo game for dangan-clj.\n"
       "In case of need, type \"help\".\n"))

(defn- game-loop [state]
  (print (make-prompt state))
  (flush)
  (let [command (read-line)
        next-state (evaluate-command state command)
        command-output (present-state next-state command)]
    (when command-output
      (println command-output))
    (recur next-state)))

(defn -main
  [& args]
  (println welcome-text)
  (let [state (make-initial-state test-scene)]
    (present-state state "")
    (game-loop state)))
