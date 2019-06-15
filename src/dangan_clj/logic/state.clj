(ns dangan-clj.logic.state
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.logic.game :as game]))

(s/def ::clues vector?)
(s/def ::player (s/keys :req-un [::clues]))

(s/def ::current-scene keyword?)
(s/def ::mode #{:interact :dialog})
(s/def ::cli-dict (s/map-of keyword? (s/coll-of string?)))

(s/def ::dialog keyword?)
(s/def ::line int?)

(s/def ::state (s/keys :req-un [::game/game
                                ::mode
                                ::player
                                ::current-scene
                                ::cli-dict]
                       :opt-un [::dialog
                                ::line]))
