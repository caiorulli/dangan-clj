(ns dangan-clj.core
  (:gen-class)
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.entrypoint :as entrypoint]
            [dangan-clj.input.example :as example]
            [dangan-clj.logic.game :as game]))

(defn- game-loop [game content dict]
  (print (cli/prompt (:cli game) content))
  (flush)
  (let [input    (read-line)
        new-game (entrypoint/exec input content dict game)]
    (when-let [output (:output new-game)]
      (println (first output)))
    (recur new-game content dict)))

(defn -main
  [& _]
  (let [content     example/game
        game-valid? (game/valid? content)]
    (if-not game-valid?
      (println (game/explain content))
      (let [game (entrypoint/init example/game example/cli-dict)]
        (println messages/welcome-text)
        (game-loop game content example/cli-dict)))))
