(ns dangan-clj.logic.game-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.input.example :as example]
            [dangan-clj.logic.game :as game-spec]
            [midje.sweet :refer [=> fact facts]]))

(defn valid? [game]
  (s/valid? ::game-spec/game game))

(facts
  "about game spec"
  (fact
   "empty game should be invalid"
   (valid? nil) => false)

  (fact
   "game with invalid first-scene should be invalid"
   (valid? {}) => false)

  (fact
    "example game should be valid"
    (valid? example/game) => true))

