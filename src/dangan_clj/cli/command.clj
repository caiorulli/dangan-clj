(ns dangan-clj.cli.command
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [dangan-clj.cli.dict :as dict]
            [dangan-clj.logic.navigation :as nav]
            [dangan-clj.logic.state :as state]))

(s/def ::type #{:describe :examine :help :navigate :talk})
(s/def ::target keyword?)

(s/def ::command (s/keys :req-un [::type]
                         :opt-un [::target]))

(defn make [command-string cli-dict]
  (let [lowered-command-string (string/lower-case command-string)
        command-words (string/split lowered-command-string #" ")
        first-word    (first command-words)
        predicate     (string/join " " (rest command-words))]
    (cond
      (= first-word "describe") {:type :describe}
      (= first-word "help")     {:type :help}
      (= first-word "examine")  {:type :examine
                                 :target (dict/lookup cli-dict predicate)}
      (= first-word "enter")    {:type :navigate
                                 :target (dict/lookup cli-dict predicate)}
      (string/starts-with? lowered-command-string "talk to")
      {:type :talk
       :target (dict/lookup cli-dict (last command-words))})))

(defmulti evaluate
  (fn [command state]
    (if (= (:mode state) :dialog)
      :advance-dialog
      (and (s/valid? ::command command)
           (:type command)))))

(defmethod evaluate :describe [command state]
  (state/describe state))

(defmethod evaluate :navigate [command state]
  (nav/go-to state (:target command)))

(defmethod evaluate :examine [command state]
  (state/examine state (:target command)))

(defmethod evaluate :talk [command state]
  (state/talk-to state (:target command)))

(defmethod evaluate :advance-dialog [command state]
  (state/advance-dialog state))

(defmethod evaluate :default [command state]
  state)
