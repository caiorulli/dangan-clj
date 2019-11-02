(ns dangan-clj.entrypoint
  (:require [dangan-clj.cli.cli :as cli]))

(defn init
  [content]
  #:game {:log        []
          :cli        nil
          :content    content})

(defn line
  [{:game/keys [cli content]}]
  (let [{:keys [current-dialog current-line]} cli]
    (when (and current-dialog current-line)
      (let [[char-id line-text] (-> content :dialogs current-dialog (nth current-line))
            char-name (-> content :characters char-id :display-name)]
        #:line {:speaker char-name
                :text    line-text}))))
