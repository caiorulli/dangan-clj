(ns dangan-clj.cli.cli
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [dangan-clj.cli.dict :refer [lookup]]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.logic.navigation :as nav]
            [dangan-clj.logic.state :as state]))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:display-name (state/get-current-scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  {:post [(s/valid? ::state/state %)]}
  (if (= (:mode state) :interact)
    (if (nil? command)
      state
      (cond
        (= (:type command) :examine) (state/examine state (:target command))
        (= (:type command) :describe) (state/describe state)
        (= (:type command) :navigate) (nav/go-to state (:target command))
        :else state))
    (state/advance-dialog state)))

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

(defn interpret [cli-dict command-string]
  (let [lowered-command-string (string/lower-case command-string)
        command-words (string/split lowered-command-string #" ")
        first-word    (first command-words)
        predicate     (string/join " " (rest command-words))]
    (cond
      (= command-string "describe") {:type :describe}
      (= command-string "help")     {:type :help}
      (= first-word "examine")      {:type :examine
                                     :target (lookup cli-dict predicate)}
      (= first-word "enter")        {:type :navigate
                                     :target (lookup cli-dict predicate)})))
