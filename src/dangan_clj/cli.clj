(ns dangan-clj.cli
  (:require [clojure.string :as string]
            [dangan-clj.game-logic :as logic]
            [dangan-clj.logic.navigation :as nav]
            [clojure.set :refer [select]]))

(def help-text
  (str "Command list:\n\n"
       "help              Displays this text.\n"
       "describe          Describes the current scene.\n"
       "examine (object)  Triggers events related to examination of points of interest.\n"))

(defn make-prompt [state]
  (if (= (:mode state) :interact)
    (str "("
         (:display-name (logic/get-current-scene state))
         ") > ")
    "..."))

(defn evaluate-command [state command]
  (if (= (:mode state) :interact)
    (if (nil? command)
      state
      (cond
        (= (:type command) :examine) (logic/examine state (:target command))
        (= (:type command) :navigate) (nav/go-to state (:target command))
        :else state))
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

(defn- get-scene [state scene-string]
  (let [game (:game state)
        scenes (:scenes game)
        scene-ids (keys scenes)]
    (first (filter #(some (partial = scene-string)
                          (:synonyms (get scenes %)))
                   scene-ids))))

(defn- get-poi [state poi-string]
  (let [current-scene (logic/get-current-scene state)
        pois (:pois current-scene)
        poi-ids (keys pois)]
    (first (filter #(some (partial = poi-string)
                          (:synonyms (get pois %)))
                   poi-ids))))

(defn interpret [state command-string]
  (let [lowered-command-string (string/lower-case command-string)
        command-words (string/split lowered-command-string #" ")
        first-word    (first command-words)
        predicate     (string/join " " (rest command-words))]
    (cond
      (= command-string "describe") {:type :describe}
      (= command-string "help")     {:type :help}
      (= first-word "examine")      {:type :examine
                                     :target (get-poi state predicate)}
      (= first-word "enter")        {:type :navigate
                                     :target (get-scene state predicate)})))
