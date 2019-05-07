(ns dangan-clj.core-test
  (:use midje.sweet)
  (:require [dangan-clj.core :refer :all]))

(defn make-class-trial []
  {})

(defn make-player []
  {:clues #{}})

(defn find-clue [player clue]
  (assoc player :clues (conj (:clues player) clue)))


(fact "about life. Should always be true, no matter what"
  (+ 2 2) => 4)

(facts "about class trials!"
       (fact "this test will be erased one day."
             (make-class-trial) => {}))

(facts "about the player."
       (fact "player should start clueless"
             (make-player) => {:clues #{}})
       (fact "along the game, the player should be able to get a clue"
             (let [player (make-player)
                   clue {}]
               (find-clue player clue) => {:clues #{clue}})))
