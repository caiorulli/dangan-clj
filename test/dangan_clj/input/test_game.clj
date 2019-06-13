(ns dangan-clj.input.test-game)

(def clue-1 {:id 1})

(def knife
  {:clue     clue-1
   :dialog   [{:speaker "Giba"
               :text    "That's the knife I used to cut tomatoes."}]
   :scene-id :gibas-room})

(def schredder
  {:dialog   [{:speaker "Thiago"
               :text    "What's that big weird machine?"}
              {:speaker "Giba"
               :text    "It's a paper schredder."}
              {:speaker "Thiago"
               :text    "Why do you even have that here?"}]
   :scene-id :gibas-room})

(def gibas-room
  {:display-name "Giba's Room"
   :description "Giba's hauntingly neat and organized room."})

(def washing-machine
  {:dialog []
   :scene-id :laundry})

(def laundry
  {:display-name "Laundry"
   :description "So this is where Giba disposes of blood-soaked clothes..."})

(def test-game
  {:scenes {:gibas-room gibas-room
            :laundry    laundry}
   :first-scene :gibas-room
   :pois   {:knife knife
            :schredder schredder
            :washing-machine washing-machine}})

(def cli-dict
  {:gibas-room #{"room" "giba's room"}
   :laundry    #{"laundry" "laundry area"}
   :knife      #{"knife"}
   :schredder  #{"schredder" "black box" "box"}
   :washing-machine #{"washing-machine"}})
