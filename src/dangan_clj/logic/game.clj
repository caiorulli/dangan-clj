(ns dangan-clj.logic.game
  (:require [clojure.spec.alpha :as s]))

(s/def ::first-scene keyword?)

(s/def ::game (s/keys :req-un [::first-scene]))

(defn get-scene [game scene-id]
  (scene-id (:scenes game)))
