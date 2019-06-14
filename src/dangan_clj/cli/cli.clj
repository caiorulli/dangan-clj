(ns dangan-clj.cli.cli
  (:require [clojure.string :as string]
            [dangan-clj.game-logic :as logic]
            [dangan-clj.logic.navigation :as nav]
            [dangan-clj.cli.messages :as messages]))

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
      (= (:type command) :help) messages/help-text)
    (let [line ((:dialog state) (:line state))
          [speaker-id text] line
          character (get (:characters (:game state)) speaker-id)
          character-name (:display-name character)]
      (str character-name ": " text))))

(defn- find-id-from-cli-dict
  [cli-dict predicate possible-ids]
  (first (filter #(some (partial = predicate)
                        (get cli-dict %))
                 possible-ids)))

(defn- get-scene [state scene-string]
  (let [cli-dict (:cli-dict state)
        scene-ids (keys (:scenes (:game state)))]
    (find-id-from-cli-dict cli-dict scene-string scene-ids)))

(defn- get-poi [state poi-string]
  (let [cli-dict (:cli-dict state)
        all-pois (:pois (:game state))
        poi-ids (filter #(= (:current-scene state) (:scene-id (% all-pois))) (keys all-pois))]
    (find-id-from-cli-dict cli-dict poi-string poi-ids)))

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
