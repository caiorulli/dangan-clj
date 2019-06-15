(ns dangan-clj.logic.state-test
  (:require  [midje.sweet :refer [facts fact =>]]
             [clojure.spec.alpha :as s]
             [dangan-clj.input.test-game :as test-game]
             [dangan-clj.logic.state :as state]))

(def valid-player {:clues []})
(def valid-state {:game test-game/test-game
                  :mode :interact
                  :player valid-player
                  :current-scene :rodrigos-room
                  :cli-dict {:lala #{"lala" "lalala"}}})

(facts "about state validation"
       (fact "should contain required fields"
             (s/valid? ::state/state nil) => false
             (s/valid? ::state/state {}) => false

             (s/valid? ::state/state {:game test-game/test-game
                                      :player valid-player}) => false
             (s/valid? ::state/state {:mode :interact
                                      :player valid-player}) => false
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :lala
                                      :player valid-player}) => false
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :interact}) => false
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :interact
                                      :player valid-player}) => false
             (s/valid? ::state/state {:game test-game/test-game
                                      :mode :interact
                                      :player valid-player
                                      :current-scene :rodrigos-room}) => false

             (s/valid? ::state/state valid-state) => true)

       (fact "might contain optional fields"
             (s/valid? ::state/state (merge valid-state {:current-dialog :knife-dialog
                                                         :current-line 0})) => true
             (s/valid? ::state/state (merge valid-state {:current-dialog "lala"
                                                         :current-line []})) => false))

(facts "about state functions"
       (fact "makes valid initial state"
             (s/valid? ::state/state (state/make-initial-state test-game/test-game
                                                               test-game/cli-dict)) => true))
