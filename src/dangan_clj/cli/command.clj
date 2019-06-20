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

(defmulti evaluate-state
  (fn [command state game]
    (and (s/valid? ::command command)
         (:type command))))

(defmethod evaluate-state :navigate [command state game]
  (state/go-to state (:target command) game))

(defmethod evaluate-state :default [command state game]
  state)

(defmulti evaluate-cli (fn [command cli state game]
                         (if (= (:mode cli) :dialog)
                           :advance-dialog
                           (and (s/valid? ::command command)
                                (:type command)))))

(defmethod evaluate-cli :examine [command cli state game]
  (let [target (:target command)
        target-poi (-> game :pois target)
        current-scene-id (-> state :current-scene)
        character-is-present? (not (nil? (state/presence state target game)))
        target-dialog (or (when (= (get target-poi :scene-id) current-scene-id)
                            (get target-poi :dialog-id))
                          (when character-is-present?
                            (-> game :characters target :dialog-id)))]
    (if target-dialog
      (cli/dialog-mode target-dialog)
      cli/interact-mode)))

(defmethod evaluate-cli :talk [command cli state game]
  (let [target (:target command)
        presence (state/presence state target game)]
    (if-not (nil? presence)
      (cli/dialog-mode (nth presence 1))
      cli/interact-mode)))

(defmethod evaluate-cli :describe [command cli state game]
  (cli/dialog-mode (-> state (state/current-scene game) :dialog-id)))

(defmethod evaluate-cli :advance-dialog [command cli state game]
  (cli/next-line cli game))

(defmethod evaluate-cli :default [command cli state game]
  cli/interact-mode)
