(ns dangan-clj.logic.state
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.logic.game :as game]))

(s/def ::clues vector?)
(s/def ::player (s/keys :req-un [::clues]))
(s/def ::current-scene keyword?)

(s/def ::state (s/keys :req-un [::game/game
                                ::player
                                ::current-scene]))

(defn- player []
  {:clues []})

(defn initial-state [game]
  {:player        (player)
   :game          game
   :current-scene (:first-scene game)})

(defn current-scene [state game]
  ((:current-scene state) (:scenes game)))

(defn presence [state character-id game]
  (some #(when (= (first %) character-id) %)
        (:presences (current-scene state game))))
