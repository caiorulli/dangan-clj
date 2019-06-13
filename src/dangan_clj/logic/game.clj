(ns dangan-clj.logic.game
  (:require [clojure.spec.alpha :as s]))

(s/def ::first-scene keyword?)

(s/def ::dialog vector?)
(s/def ::clue map?)
(s/def ::scene-id keyword?)
(s/def ::poi (s/keys :req-un [::dialog ::scene-id]
                     :opt-un [::clue]))
(s/def ::poi-id keyword?)
(s/def ::pois (s/map-of ::poi-id ::poi))

(s/def ::display-name string?)
(s/def ::description string?)
(s/def ::scene (s/keys :req-un [::display-name ::description]))
(s/def ::scenes (s/map-of ::scene-id ::scene))

(s/def ::game (s/keys :req-un [::first-scene ::scenes ::pois]))

(defn valid? [game]
  (s/valid? ::game game))

(defn explain [game]
  (s/explain-str ::game game))
