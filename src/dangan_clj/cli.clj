(ns dangan-clj.cli
  (:require [clojure.string :as string]
            [dangan-clj.game-logic :as logic]))

(def help-text
  (str "Command list:\n\n"
       "help              Displays this text.\n"
       "describe          Describes the current scene.\n"
       "examine (object)  Triggers events related to examination of points of interest.\n"))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:name (:scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  (if (= (:mode state) :interact)
    (let [command-words (string/split command #" ")
          verb          (first command-words)
          predicate     (last command-words)]
      (if (= verb "examine")
        (logic/examine state predicate)
        state))
    (logic/advance-dialog state)))

(defn- present-look [state]
  (-> state :scene :description))

(defn present-state [state command]
  (if (= (:mode state) :interact)
    (cond
      (= command "describe") (present-look state)
      (= command "help") help-text)
    (str (:speaker state) ": "
         (:text    state))))
