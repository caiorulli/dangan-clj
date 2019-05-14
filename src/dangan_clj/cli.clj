(ns dangan-clj.cli
  (:require [clojure.string :refer [split trim]]
            [dangan-clj.game-logic :as logic]))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:name (:scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  (if (= (:mode state) :interact)
    (logic/interact-with  state (last (split command #" ")))
    (logic/advance-dialog state)))

(defn- present-look [state]
  (trim (apply str (map #(str (:name %) " ")
                        (:pois (:scene state))))))

(defn present-state [state command]
  (if (= (:mode state) :interact)
    (if (= command "look")
      (present-look state)
      (:description (:scene state)))
    (str (:speaker state) ": "
         (:text    state))))
