(ns dangan-clj.cli.cli
  (:require [dangan-clj.cli.messages :as messages]
            [dangan-clj.logic.state :as state]
            [clojure.spec.alpha :as s]))

(s/def ::mode #{:interact :dialog})
(s/def ::current-dialog keyword?)
(s/def ::current-line int?)

(s/def ::cli (s/keys :req-un [::mode]
                     :opt-un [::current-dialog ::current-line]))

(defn prompt [state]
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

;; New format methods

(defn- is-thought? [speaker-id]
  (= speaker-id :thought))

(defn- dialog-output [cli state]
  (let [dialog-id (:current-dialog cli)
        line-number (:current-line cli)
        line (-> state :game :dialogs dialog-id (nth line-number))
        [speaker-id text] line
        character-name (-> state :game :characters speaker-id :display-name)]
    (if (is-thought? speaker-id)
      text
      (str character-name ": " text))))

(defn output [cli state command]
  (cond
    (= (:mode cli) :dialog)
    (dialog-output cli state)
    (= (:type command) :help)
    messages/help-text))

(def interact-mode
  {:mode :interact})

(defn dialog-mode [dialog-id]
  {:mode :dialog
   :current-dialog dialog-id
   :current-line   0})

(defn next-line [cli state]
  (let [dialog-id (:current-dialog cli)
        next-line-number (-> cli :current-line inc)
        dialog (-> state :game :dialogs dialog-id)]
    (if (= (count dialog) next-line-number)
      interact-mode
      (merge cli {:current-line next-line-number}))))
