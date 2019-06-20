(ns dangan-clj.cli.cli
  (:require [dangan-clj.cli.messages :as messages]
            [dangan-clj.logic.state :as state]
            [clojure.spec.alpha :as s]))

(s/def ::mode #{:interact :dialog})
(s/def ::current-dialog keyword?)
(s/def ::current-line int?)

(s/def ::cli (s/keys :req-un [::mode]
                     :opt-un [::current-dialog ::current-line]))

(defn prompt [cli state game]
  (if (= (:mode cli) :interact)
    (str "("
         (:display-name (state/current-scene state game))
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

(def interact-mode
  {:mode :interact})

(defn dialog-mode [dialog-id]
  {:mode :dialog
   :current-dialog dialog-id
   :current-line   0})

(defn next-line [cli game]
  (let [dialog-id (:current-dialog cli)
        next-line-number (-> cli :current-line inc)
        dialog (-> game :dialogs dialog-id)]
    (if (= (count dialog) next-line-number)
      interact-mode
      (merge cli {:current-line next-line-number}))))
