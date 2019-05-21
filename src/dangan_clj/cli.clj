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
         (:name (logic/get-current-scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  (if (= (:mode state) :interact)
    (if (nil? command)
      state
      (if (= (:type command) :examine)
        (logic/examine state (:target command))
        state))
    (logic/advance-dialog state)))

(defn- present-look [state]
  (-> state logic/get-current-scene :description))

(defn present-state [state command]
  (if (= (:mode state) :interact)
    (cond
      (= (:type command) :describe) (present-look state)
      (= (:type command) :help) help-text)
    (let [line ((:dialog state) (:line state))]
      (str (:speaker line) ": "
           (:text    line)))))

(defn interpret [command-string]
  (let [command-words (string/split command-string #" ")
        first-word    (first command-words)
        last-word     (last  command-words)]
    (cond
      (= command-string "describe") {:type :describe}
      (= command-string "help")     {:type :help}
      (= first-word "examine")      {:type :examine
                                     :target last-word})))
