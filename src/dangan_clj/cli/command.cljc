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

(defmulti evaluate-cli (fn [command cli _]
                         (if (= (:mode cli) :dialog)
                           :advance-dialog
                           (:type command))))

(defmethod evaluate-cli :examine [command cli content]
  (cli/examine cli content (:target command)))

(defmethod evaluate-cli :talk [command cli content]
  (let [target        (:target command)
        current-scene (-> cli :player (player/current-scene content))
        presence      (game/presence target current-scene)]
    (if-not (nil? presence)
      (cli/dialog-mode cli (nth presence 1))
      (cli/interact-mode cli))))

(defmethod evaluate-cli :describe [_ cli content]
  (cli/dialog-mode cli (-> cli :player (player/current-scene content) :dialog-id)))

(defmethod evaluate-cli :navigate [command cli content]
  (update cli :player #(player/go-to % (:target command) content)))

(defmethod evaluate-cli :advance-dialog [_ cli content]
  (cli/next-line cli content))

(defmethod evaluate-cli :help [_ cli _]
  (cli/simple-text-mode cli :help))

(defmethod evaluate-cli :list-clues [_ cli _]
  (cli/simple-text-mode cli :list-clues))

(defmethod evaluate-cli :default [_ cli _]
  (cli/interact-mode cli))

(defn new-game
  [{:game/keys [cli content dictionary] :as game} input]
  (assoc game
         :game/cli
         (evaluate-cli (make input dictionary) cli content)))
