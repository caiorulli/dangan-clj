(ns dangan-clj.cli.cli
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.logic.player :as player]))

(s/def ::mode #{:interact :dialog})
(s/def ::current-dialog keyword?)
(s/def ::current-line int?)

(s/def ::cli (s/keys :req-un [::mode ::player/player]
                     :opt-un [::current-dialog ::current-line]))

(defn prompt [cli game]
  (if (= (:mode cli) :interact)
    (str "("
         (:display-name (player/current-scene (:player cli) game))
         ") > ")
    "..."))

(defn- is-thought? [speaker-id]
  (= speaker-id :thought))

(defn- dialog-output [cli game]
  (let [dialog-id (:current-dialog cli)
        line-number (:current-line cli)
        line (-> game :dialogs dialog-id (nth line-number))
        [speaker-id text] line
        character-name (-> game :characters speaker-id :display-name)]
    (if (is-thought? speaker-id)
      text
      (str character-name ": " text))))

(defn output [cli game command]
  (cond
    (= (:mode cli) :dialog)
    (dialog-output cli game)
    (= (:type command) :help)
    messages/help-text))

(defn cli [game]
  {:mode :interact
   :player (player/initial-state game)})

(defn interact-mode [cli]
  (-> cli
      (merge {:mode :interact})
      (dissoc :current-dialog)
      (dissoc :current-line)))

(defn dialog-mode [cli dialog-id]
  (merge cli {:mode :dialog
              :current-dialog dialog-id
              :current-line   0}))

(defn next-line [cli game]
  (let [dialog-id (:current-dialog cli)
        next-line-number (-> cli :current-line inc)
        dialog (-> game :dialogs dialog-id)]
    (if (= (count dialog) next-line-number)
      (interact-mode cli)
      (merge cli {:current-line next-line-number}))))
