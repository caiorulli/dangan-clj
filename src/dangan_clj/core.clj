(ns dangan-clj.core
  (:gen-class)
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.command :as command]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.input.example :as example]
            [dangan-clj.logic.game :as game]))

(defn- game-loop [cli game dict]
  (print (cli/prompt cli game))
  (flush)
  (let [next-cli (-> (read-line)
                     (command/make dict)
                     (command/evaluate-cli cli game))
        command-output (cli/output next-cli game)]
    (when command-output
      (println command-output))
    (recur next-cli game dict)))

(defn -main
  [& _]
  (let [game example/game
        game-valid? (game/valid? game)]
    (if-not game-valid?
      (println (game/explain game))
      (let [cli (cli/cli game)]
        (println messages/welcome-text)
        (game-loop cli game example/cli-dict)))))
