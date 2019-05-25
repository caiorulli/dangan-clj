(ns dangan-clj.logic.game-spec-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.game
             [example :as example]
             [spec :as game-spec]]
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
  (let [prop-nil (dissoc example/arandu-game :first-scene)]
    (valid? prop-nil) => false))

 (fact
  "example game should be valid"
  (valid? example/arandu-game)))
