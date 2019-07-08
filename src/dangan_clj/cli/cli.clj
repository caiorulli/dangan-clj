(ns dangan-clj.cli.cli
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.cli.messages :as messages]
            [dangan-clj.logic.game :refer [is-thought?]]
            [dangan-clj.logic.player :as player]
            [dangan-clj.logic.game :as game]))

(s/def ::mode #{:interact :dialog})
(s/def ::current-dialog keyword?)
(s/def ::current-line int?)
(s/def ::simple-text keyword?)
(s/def ::effects (s/coll-of string?))

(s/def ::cli (s/keys :req-un [::mode ::player/player]
                     :opt-un [::current-dialog
                              ::current-line
                              ::simple-text
                              ::effects]))

(defn cli [game]
  {:mode :interact
   :player (player/player game)})

(defn interact-mode [cli]
  (-> cli
      (merge {:mode :interact})
      (dissoc :current-dialog)
      (dissoc :current-line)
      (dissoc :effects)
      (dissoc :simple-text)))

(defn simple-text-mode [cli text-type]
  (assoc cli :simple-text text-type))

(defn dialog-mode
  ([cli dialog-id]
   (merge cli {:mode :dialog
               :current-dialog dialog-id
               :current-line 0
               :effects []}))
  ([cli dialog-id effect]
   (if (nil? effect)
     (dialog-mode cli dialog-id)
     (merge cli {:mode :dialog
                 :current-dialog dialog-id
                 :current-line   0
                 :effects [effect]}))))

(defn examine [cli game target]
  (let [target-poi (-> game :pois target)
        current-scene-id (-> cli :player :current-scene)
        current-scene (player/current-scene (:player cli) game)
        poi-in-scene? (= (get target-poi :scene-id) current-scene-id)
        present? (game/character-is-present? target current-scene)
        clue-in-poi (game/clue-id-from-poi-id target game)
        clue (when clue-in-poi
               (-> game :clues clue-in-poi))]
    (cond
      poi-in-scene?
      (-> cli
          (update :player #(player/with-clue % clue-in-poi))
          (dialog-mode (get target-poi :dialog-id)
                       (when clue
                         (str "Obtained clue: "
                              (get clue :display-name)))))

      present?
      (dialog-mode cli (game/character-description-dialog-id target game))

      :else
      (interact-mode cli))))

(defn next-line [cli game]
  (let [dialog-id (:current-dialog cli)
        next-line-number (-> cli :current-line inc)
        dialog (-> game :dialogs dialog-id)
        dialog-will-be-finished? (<= (count dialog) next-line-number)
        dialog-has-finished? (<= (count dialog) (get cli :current-line))
        has-effect? (not (empty? (:effects cli)))]
    (cond
      dialog-has-finished?
      (interact-mode cli)

      (or (not dialog-will-be-finished?)
          has-effect?)
      (merge cli {:current-line next-line-number})

      :else
      (interact-mode cli))))

(defn prompt [cli game]
  (if (= (:mode cli) :interact)
    (str "("
         (:display-name (player/current-scene (:player cli) game))
         ") > ")
    "..."))

(defn- dialog-output [cli game]
  (let [dialog-id (:current-dialog cli)
        line-number (:current-line cli)
        dialog (-> game :dialogs dialog-id)
        dialog-has-finished? (= (count dialog) line-number)
        has-effect? (count (:effects dialog))]
    (cond
      (not dialog-has-finished?)
      (let [line (game/line game dialog-id line-number)
            [speaker-id text] line
            character-name (-> game :characters speaker-id :display-name)]
        (if (is-thought? speaker-id)
          text
          (str character-name ": " text)))

      has-effect?
      (str "** "
           (first (:effects cli))
           " **"))))

(defn- simple-text-mode? [cli]
  (and (= (:mode cli) :interact)
       (not (nil? (:simple-text cli)))))

(defn output [cli game]
  (cond
    (= (:mode cli) :dialog)
    (dialog-output cli game)

    (simple-text-mode? cli)
    (messages/simple-text cli game)))
