(ns dangan-clj.cli.command
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [dangan-clj.cli.dict :as dict]
            [dangan-clj.logic.navigation :as nav]
            [dangan-clj.logic.state :as state]
            [dangan-clj.cli.cli :as cli]))

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

(defmulti evaluate-state (fn [command state]
  (if (= (:mode state) :dialog)
    :advance-dialog
    (and (s/valid? ::command command)
         (:type command)))))

(defmethod evaluate-state :describe [command state]
  (state/describe state))

(defmethod evaluate-state :navigate [command state]
  (nav/go-to state (:target command)))

(defmethod evaluate-state :examine [command state]
  (state/examine state (:target command)))

(defmethod evaluate-state :talk [command state]
  (state/talk-to state (:target command)))

(defmethod evaluate-state :advance-dialog [command state]
  (state/advance-dialog state))

(defmethod evaluate-state :default [command state]
  state)


(defmulti evaluate-cli (fn [command cli state]
    (if (= (:mode cli) :dialog)
      :advance-dialog
      (and (s/valid? ::command command)
           (:type command)))))

(defmethod evaluate-cli :examine [command cli state]
  (let [target (:target command)
        target-poi (-> state :game :pois target)
        current-scene-id (-> state :current-scene)
        character-is-present? (not (nil? (state/presence state target)))
        target-dialog (or (when (= (get target-poi :scene-id) current-scene-id)
                            (get target-poi :dialog-id))
                          (when character-is-present?
                            (-> state :game :characters target :dialog-id)))]
    (if target-dialog
      (cli/dialog-mode target-dialog)
      cli/interact-mode)))

(defmethod evaluate-cli :talk [command cli state]
  (let [target (:target command)
        presence (state/presence state target)]
    (if-not (nil? presence)
      (cli/dialog-mode (nth presence 1))
      cli/interact-mode)))

(defmethod evaluate-cli :describe [command cli state]
    (cli/dialog-mode (-> state (state/current-scene) :dialog-id)))

(defmethod evaluate-cli :advance-dialog [command cli state]
  (cli/next-line cli state))

(defmethod evaluate-cli :default [command cli state]
  cli/interact-mode)
