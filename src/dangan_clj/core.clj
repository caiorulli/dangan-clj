(ns dangan-clj.core
  (:gen-class)
  (:require [dangan-clj
             [cli :refer [evaluate-command make-prompt present-state]]
             [game-logic :refer [make-initial-state]]]
            [dangan-clj.game.example :as example]))

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
  (let [state (make-initial-state example/arandu-game)]
    (present-state state "")
    (game-loop state)))
