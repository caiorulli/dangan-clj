(ns dangan-clj.cli
  (:require [clojure.string :as string]
            [dangan-clj.game-logic :as logic]))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:name (:scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  (if (= (:mode state) :interact)
    (logic/interact-with  state (last (string/split command #" ")))
    (logic/advance-dialog state)))

(defn- present-look [state]
  (string/trim (string/join (map #(str (:name %) " ")
                                 (:pois (:scene state))))))

(defn present-state [state command]
  (if (= (:mode state) :interact)
    (when (= command "look")
      (present-look state))
    (str (:speaker state) ": "
         (:text    state))))
