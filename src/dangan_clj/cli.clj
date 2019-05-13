(ns dangan-clj.cli
  (:require [dangan-clj.game-logic :refer [interact-with
                                           advance-dialog]]))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:name (:scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  (if (= (:mode state) :interact)
    (interact-with command state)
    (advance-dialog state)))

(defn present-state [state]
  (if (= (:mode state) :interact)
    (:description (:scene state))
    (str (:speaker state) ": "
         (:text    state))))
