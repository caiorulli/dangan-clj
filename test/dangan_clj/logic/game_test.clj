(ns dangan-clj.logic.game-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.game :as game]
            [midje.sweet :refer [=> fact facts]]))

(facts "about game validity"
  (fact "nil should not work"
    (s/valid? ::game/game nil) => false)

  (fact "should contain a first-scene keyword"
    (s/valid? ::game/game {}) => false)

  (fact "test game should work"
    (s/valid? ::game/game test-game/test-game) => true))

(facts "about scene validity"
  (fact "scenes should have a display-name"
    (s/valid? ::game/scene {:description "lala"}) => false)

  (fact "scenes should have a description"
    (s/valid? ::game/scene {:display-name "lala"}) => false)

  (fact "giba's room scene should be valid"
    (s/valid? ::game/scene test-game/gibas-room) => true))

(facts "about point-of-interest validity"
  (fact "should have associated dialog"
    (s/valid? ::game/poi {}) => false)

  (fact "knife poi should be valid"
    (s/valid? ::game/poi test-game/knife) => true))
