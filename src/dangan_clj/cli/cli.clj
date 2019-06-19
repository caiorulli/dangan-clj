(ns dangan-clj.cli.cli
  (:require [dangan-clj.cli.messages :as messages]
            [dangan-clj.logic.state :as state]))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:display-name (state/current-scene state))
         ") > ")
    "..."))

(defn- present-look [state]
  (-> state state/current-scene :description))

(defn present-state [state command]
  (if (= (:mode state) :interact)
    (cond
      (= (:type command) :help) messages/help-text)
    (let [dialog-id (:current-dialog state)
          dialog (get (:dialogs (:game state)) dialog-id)
          line (dialog (:current-line state))
          [speaker-id text] line
          is-thought? (= speaker-id :thought)
          character (get (:characters (:game state)) speaker-id)
          character-name (:display-name character)]
      (if is-thought?
        text
        (str character-name ": " text)))))
