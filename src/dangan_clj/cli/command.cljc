(ns dangan-clj.cli.command
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.dict :as dict]
            [dangan-clj.logic.game :as game]
            [dangan-clj.logic.player :as player]))

(s/def ::type #{:describe :examine :help :navigate :talk})
(s/def ::target keyword?)

(s/def ::command (s/keys :req-un [::type]
                         :opt-un [::target]))

(defn- make-predicate [command-words]
  #(string/join " " (subvec command-words %)))

(defn make [command-string cli-dict]
  (let [lowered-command-string (string/lower-case command-string)
        command-words          (string/split lowered-command-string #" ")
        first-word             (first command-words)
        predicate-after        (make-predicate command-words)]
    (cond
      (= first-word "describe")
      {:type :describe}

      (= first-word "help")
      {:type :help}

      (= first-word "examine")
      {:type   :examine
       :target (dict/lookup cli-dict (predicate-after 1))}

      (string/starts-with? lowered-command-string "go to")
      {:type   :navigate
       :target (dict/lookup cli-dict (predicate-after 2))}

      (string/starts-with? lowered-command-string "talk to")
      {:type   :talk
       :target (dict/lookup cli-dict (predicate-after 2))}

      (string/starts-with? lowered-command-string "list clue")
      {:type :list-clues})))

(defmulti evaluate-cli (fn [command cli game]
                         (if (= (:mode cli) :dialog)
                           :advance-dialog
                           (:type command))))

(defmethod evaluate-cli :examine [command cli game]
  (cli/examine cli game (:target command)))

(defmethod evaluate-cli :talk [command cli game]
  (let [target        (:target command)
        current-scene (-> cli :player (player/current-scene game))
        presence      (game/presence target current-scene)]
    (if-not (nil? presence)
      (cli/dialog-mode cli (nth presence 1))
      (cli/interact-mode cli))))

(defmethod evaluate-cli :describe [_ cli game]
  (cli/dialog-mode cli (-> cli :player (player/current-scene game) :dialog-id)))

(defmethod evaluate-cli :navigate [command cli game]
  (update cli :player #(player/go-to % (:target command) game)))

(defmethod evaluate-cli :advance-dialog [_ cli game]
  (cli/next-line cli game))

(defmethod evaluate-cli :help [_ cli _]
  (cli/simple-text-mode cli :help))

(defmethod evaluate-cli :list-clues [_ cli _]
  (cli/simple-text-mode cli :list-clues))

(defmethod evaluate-cli :default [_ cli _]
  (cli/interact-mode cli))