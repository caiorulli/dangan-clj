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
  [{:game/keys [log] :as game} input]
  (let [new-game (command/new-game game input)]
    (assoc new-game
           :game/log
           (if (seq (line new-game))
             (cons (line new-game) log)
             log))))
