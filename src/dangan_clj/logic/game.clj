(ns dangan-clj.logic.game
  (:require [clojure.spec.alpha :as s]))

(s/def ::first-scene keyword?)
(s/def ::display-name string?)
(s/def ::description string?)

(s/def ::line (s/tuple ::character-id string?))
(s/def ::dialog (s/coll-of ::line))
(s/def ::dialog-id keyword?)
(s/def ::dialogs (s/map-of ::dialog-id ::dialog))

(s/def ::character-id keyword?)
(s/def ::character (s/keys :req-un [::display-name ::dialog-id]))
(s/def ::characters (s/map-of ::character-id ::character))

(s/def ::clue-id keyword?)
(s/def ::scene-id keyword?)
(s/def ::poi (s/keys :req-un [::dialog-id ::scene-id]
                     :opt-un [::clue]))

(s/def ::poi-id keyword?)
(s/def ::pois (s/map-of ::poi-id ::poi))

(s/def ::presence (s/tuple ::character-id ::dialog-id))
(s/def ::presences (s/coll-of ::presence))

(s/def ::scene (s/keys :req-un [::display-name ::dialog-id]
                       :opt-un [::presences]))
(s/def ::scenes (s/map-of ::scene-id ::scene))

(s/def ::game (s/keys :req-un [::first-scene
                               ::scenes
                               ::pois
                               ::dialogs
                               ::characters]))

(defn valid? [game]
  (s/valid? ::game game))

(defn explain [game]
  (s/explain-str ::game game))

(defn is-thought? [speaker-id]
  (= speaker-id :thought))

(defn presence [character-id current-scene]
  (some #(when (= (first %) character-id) %)
        (:presences current-scene)))

(defn character-is-present? [character-id scene]
  (not (nil? (presence character-id scene))))

(defn character-description-dialog-id [character-id game]
  (-> game :characters character-id :dialog-id))

(defn line [game dialog-id line-number]
  (-> game :dialogs dialog-id (nth line-number)))
