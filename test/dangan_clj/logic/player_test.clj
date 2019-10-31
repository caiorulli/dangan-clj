(ns dangan-clj.logic.player-test
  (:require [clojure.spec.alpha :as s]
            [dangan-clj.input.consts :as consts]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.logic.player :as player]
            [midje.sweet :refer [=> fact facts]]))

(facts "about player functions"
  (fact "makes valid initial player"
    (s/valid? ::player/player
              (player/player test-game/test-game)) => true))

(facts "about clues"
  (fact "player should start clueless"
    (-> consts/initial-player
        :clues) => #{})

  (fact "with-clue should add clue to the collection, if it doesn't exist"
    (-> consts/initial-player
        (player/with-clue :bloody-knife)
        :clues)
    => #{:bloody-knife}

    (-> consts/initial-player
        (player/with-clue :bloody-knife)
        (player/with-clue :bloody-knife)
        :clues)
    => #{:bloody-knife}))

(facts "about navigation"
  (fact "navigating to nil should just return same player"
    (player/go-to consts/initial-player nil test-game/test-game) => consts/initial-player)

  (fact "navigating to inexisting symbol should also just return player"
    (player/go-to consts/initial-player :rollercoaster test-game/test-game) => consts/initial-player)

  (fact "navigating to existing scene should change current scene"
    (-> (player/go-to consts/initial-player :laundry test-game/test-game)
        :current-scene) => :laundry))
