(ns dangan-clj.entrypoint
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.command :as command]))

(defn init
  [content dictionary]
  #:game {:output     []
          :cli        (cli/cli content)
          :content    content
          :dictionary dictionary})

(defn exec
  [input {:game/keys [output cli content dictionary] :as game}]
  (let [command    (command/make input dictionary)
        new-cli    (command/evaluate-cli command cli content)
        new-output (cli/output new-cli content)]
    (assoc game
           :game/output (if (seq new-output)
                          (cons new-output output)
                          output)
           :game/cli new-cli)))
