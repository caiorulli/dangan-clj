(ns dangan-clj.cli.command
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [dangan-clj.cli.cli :as cli]
            [dangan-clj.cli.dict :as dict]
            [dangan-clj.logic.state :as state]))

(s/def ::type #{:describe :examine :help :navigate :talk})
(s/def ::target keyword?)

(s/def ::command (s/keys :req-un [::type]
                         :opt-un [::target]))

(defn- make-predicate [command-words]
  #(string/join " " (subvec command-words %)))

(defn make [command-string cli-dict]
  (let [lowered-command-string (string/lower-case command-string)
        command-words (string/split lowered-command-string #" ")
        first-word    (first command-words)
        predicate-after  (make-predicate command-words)]
    (cond
      (= first-word "describe")
      {:type :describe}

      (= first-word "help")
      {:type :help}

      (= first-word "examine")
      {:type :examine
       :target (dict/lookup cli-dict (predicate-after 1))}

      (string/starts-with? lowered-command-string "go to")
      {:type :navigate
       :target (dict/lookup cli-dict (predicate-after 2))}

      (string/starts-with? lowered-command-string "talk to")
      {:type :talk
       :target (dict/lookup cli-dict (predicate-after 2))})))

(defmulti evaluate-cli (fn [command cli game]
                         (if (= (:mode cli) :dialog)
                           :advance-dialog
                           (and (s/valid? ::command command)
                                (:type command)))))

(defmethod evaluate-cli :examine [command cli game]
  (let [target (:target command)
        target-poi (-> game :pois target)
        current-scene-id (-> (:state cli) :current-scene)
        character-is-present? (not (nil? (state/presence (:state cli) target game)))
        target-dialog (or (when (= (get target-poi :scene-id) current-scene-id)
                            (get target-poi :dialog-id))
                          (when character-is-present?
                            (-> game :characters target :dialog-id)))]
    (if target-dialog
      (cli/dialog-mode cli target-dialog)
      (cli/interact-mode cli))))

(defmethod evaluate-cli :talk [command cli game]
  (let [target (:target command)
        presence (state/presence (:state cli) target game)]
    (if-not (nil? presence)
      (cli/dialog-mode cli (nth presence 1))
      (cli/interact-mode cli))))

(defmethod evaluate-cli :describe [command cli game]
  (cli/dialog-mode cli (-> (:state cli) (state/current-scene game) :dialog-id)))

(defmethod evaluate-cli :navigate [command cli game]
  (update cli :state #(state/go-to % (:target command) game)))

(defmethod evaluate-cli :advance-dialog [command cli game]
  (cli/next-line cli game))

(defmethod evaluate-cli :default [command cli game]
  (cli/interact-mode cli))
