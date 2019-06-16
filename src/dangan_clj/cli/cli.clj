(ns dangan-clj.cli.cli
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.logic.navigation :as nav]
            [dangan-clj.logic.state :as state]
            [dangan-clj.cli.command :as command]))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:display-name (state/get-current-scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  {:post [(s/valid? ::state/state %)]}
  (command/evaluate command state))

(defn- present-look [state]
  (-> state state/get-current-scene :description))

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
