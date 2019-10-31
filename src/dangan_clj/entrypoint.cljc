(ns dangan-clj.entrypoint
  (:require [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.command :as command]))

(defn init
  [content dictionary]
  #:game {:log        []
          :cli        (cli/cli content)
          :content    content
          :dictionary dictionary})

(defn line
  [{:game/keys [cli content]}]
  (let [{:keys [current-dialog current-line]} cli]
    (when (and current-dialog current-line)
      (let [[char-id line-text] (-> content :dialogs current-dialog (nth current-line))
            char-name (-> content :characters char-id :display-name)]
        #:line {:speaker char-name
                :text    line-text}))))

(defn exec
  [input {:game/keys [log cli content dictionary] :as game}]
  (let [command    (command/make input dictionary)
        new-cli    (command/evaluate-cli command cli content)]
    (assoc game
           :game/log (if (seq (line {:game/cli     new-cli
                                     :game/content content}))
                       (cons (line {:game/cli     new-cli
                                    :game/content content}) log)
                       log)
           :game/cli new-cli)))
