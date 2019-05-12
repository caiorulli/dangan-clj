(ns dangan-clj.core
  (:require [dangan-clj.game-logic :refer [make-initial-state]]
            [dangan-clj.cli        :refer [make-prompt
                                           evaluate-command
                                           present-state]])
  (:gen-class))

(defn- game-loop [state]
  (print (make-prompt state))
  (flush)
  (let [command (read-line)
        next-state (evaluate-command state command)]
    (println (present-state next-state))
    (recur next-state)))

(defn -main
  [& args]
  (println "Hello, World!")
  (let [state (make-initial-state {})]
    (present-state state)
    (game-loop state)))
