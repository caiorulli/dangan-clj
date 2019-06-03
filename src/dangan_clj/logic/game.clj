(ns dangan-clj.logic.game
  (:require [clojure.spec.alpha :as s]))

(s/def ::first-scene keyword?)

(s/def ::display-name string?)
(s/def ::description string?)

(s/def ::dialog vector?)

(s/def ::poi (s/keys :req-un [::dialog]))

(s/def ::scene (s/keys :req-un [::display-name ::description]))

(s/def ::game (s/keys :req-un [::first-scene]))
