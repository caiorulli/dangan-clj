(ns dangan-clj.entrypoint
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.command :as command]))

(defn init
  [content _]
  {:output []
   :cli    (cli/cli content)})

(defn exec
  [input content dict game]
  (let [command    (command/make input dict)
        new-cli    (command/evaluate-cli command (:cli game) content)
        new-output (cli/output new-cli content)]
    {:output (if (seq new-output)
               (cons new-output (:output game))
               (:output game))
     :cli    new-cli}))
