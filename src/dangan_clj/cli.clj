(ns dangan-clj.cli
  (:require [dangan-clj.game-logic :refer [interact-with]]))

(defn make-prompt [state]
  (str "("
       (:name (:scene state))
       ") > "))

(defn evaluate-command [state command]
  (interact-with command state))

(defn present-state [state]
  (if (= (:mode state) :interact)
    state
    (str (:speaker state) ": "
         (:text    state))))
