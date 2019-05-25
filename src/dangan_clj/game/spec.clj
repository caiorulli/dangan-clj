(ns dangan-clj.game.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::first-scene #(not (nil? %)))

(s/def ::game (s/keys :req [::first-scene]))
