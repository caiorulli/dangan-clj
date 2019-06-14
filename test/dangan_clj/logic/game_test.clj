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

(facts "about dialogs"
       (fact "should be collections of lines"
             (s/valid? ::game/dialog []) => true
             (s/valid? ::game/dialog nil) => false
             (s/valid? ::game/dialog "") => false
             (s/valid? ::game/dialog [[:speaker "lala"]]) => true
             (s/valid? ::game/dialog [[]]) => false))

(facts "about characters"
       (fact "should have display-name and description"
             (s/valid? ::game/character {:display-name "Dr. Gori"}) => false
             (s/valid? ::game/character {:description "Meu objetivo é a conquista"}) => false
             (s/valid? ::game/character {:display-name "Dr. Gori"
                                         :description "Meu objetivo é a conquista"}) => true
             (s/valid? ::game/character nil) => false
             (s/valid? ::game/character "Spectreman") => false
             (s/valid? ::game/character {}) => false))

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

