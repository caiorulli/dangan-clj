(ns dangan-clj.cli
  (:require [clojure.string :refer [split]]
            [dangan-clj.game-logic :refer [interact-with
                                              advance-dialog]]))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:name (:scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  (if (= (:mode state) :interact)
    (interact-with  state (last (split command #" ")))
    (advance-dialog state)))

(defn present-state [state command]
  (if (= (:mode state) :interact)
    (:description (:scene state))
    (str (:speaker state) ": "
         (:text    state))))
