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
    (s/valid? ::game/scene {:dialog-id :lala
                            :presences []}) => false)

  (fact "scenes should have a dialog-id"
    (s/valid? ::game/scene {:display-name "lala"
                            :presences []}) => false)

  (fact "scenes can omit a presences list"
    (s/valid? ::game/scene {:display-name "lala"
                            :dialog-id :lala}) => true)

  (fact "giba's room scene should be valid"
    (s/valid? ::game/scene test-game/gibas-room) => true
    (s/valid? ::game/scene test-game/laundry) => true))

(facts "about point-of-interest validity"
  (fact "should have associated dialog id and scene id"
    (s/valid? ::game/poi {}) => false
    (s/valid? ::game/poi {:dialog-id []}) => false
    (s/valid? ::game/poi {:scene-id :lala}) => false)

  (fact "pois can omit associated clue"
    (s/valid? ::game/poi {:dialog-id :lalala
                          :scene-id :lala}) => true)

  (fact "knife poi should be valid"
    (s/valid? ::game/poi test-game/knife) => true))

(facts "about presences validity"
  (fact "should be coll of two keywords"
    (s/valid? ::game/presences []) => true
    (s/valid? ::game/presences [[:lala :lala]]) => true
    (s/valid? ::game/presences [["lala" "lala"]]) => false))

(facts "about dialogs"
  (fact "should be collections of lines"
    (s/valid? ::game/dialog []) => true
    (s/valid? ::game/dialog nil) => false
    (s/valid? ::game/dialog "") => false
    (s/valid? ::game/dialog [[:speaker "lala"]]) => true
    (s/valid? ::game/dialog [[]]) => false))

(facts "about characters"
  (fact "should have display-name and dialog-id"
    (s/valid? ::game/character {:display-name "Dr. Gori"}) => false
    (s/valid? ::game/character {:dialog-id :lala}) => false
    (s/valid? ::game/character {:display-name "Dr. Gori"
                                :dialog-id :lala}) => true
    (s/valid? ::game/character nil) => false
    (s/valid? ::game/character "Spectreman") => false
    (s/valid? ::game/character {}) => false))

(facts "about clues"
  (fact "clues map should only contain clue description"
    (s/valid? ::game/clues nil) => false
    (s/valid? ::game/clues {}) => true
    (s/valid? ::game/clues {:clue1 "the horror"}) => false

    (s/valid? ::game/clues {:clue1 {:description "the horror"
                                    :display-name "the horror"}}) => true))

(facts "about wrapper fns"
  (fact "valid? wraps clojure.spec valid? fn"
    (let [wrapper-fn #(game/valid? %)
          spec-fn #(s/valid? ::game/game %)]
      (wrapper-fn nil) => (spec-fn nil)
      (wrapper-fn test-game/test-game) => (spec-fn test-game/test-game)))

  (fact "explain wraps clojure.spec explain-str fn"
    (let [wrapper-fn #(game/explain %)
          spec-fn #(s/explain-str ::game/game %)]
      (wrapper-fn nil) => (spec-fn nil)
      (wrapper-fn {}) => (spec-fn {}))))

(facts "about search fns"
  (fact "clue-id-from-poi-id behaviour"
    (game/clue-id-from-poi-id :washing-machine test-game/test-game)
    => nil

    (game/clue-id-from-poi-id :lala test-game/test-game)
    => nil

    (game/clue-id-from-poi-id :knife test-game/test-game)
    => :bloody-knife))
