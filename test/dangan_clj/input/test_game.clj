(ns dangan-clj.input.test-game)

(def clue-1 {:id 1})

(def knife
  {:clue     clue-1
   :dialog   [{:speaker "Giba"
               :text    "That's the knife I used to cut tomatoes."}]})

(def schredder
  {:dialog   [{:speaker "Thiago"
               :text    "What's that big weird machine?"}
              {:speaker "Giba"
               :text    "It's a paper schredder."}
              {:speaker "Thiago"
               :text    "Why do you even have that here?"}]})

(def gibas-room
  {:display-name "Giba's Room"
   :description "Giba's hauntingly neat and organized room."
   :pois {:knife     knife
          :schredder schredder}})

(def laundry
  {:pois {}})

(def test-game
  {:scenes {:gibas-room gibas-room
            :laundry    laundry}
   :first-scene :gibas-room})

(def cli-dict
  {:gibas-room #{"room" "giba's room"}
   :laundry    #{"laundry" "laundry area"}
   :knife      #{"knife"}
   :schredder  #{"schredder" "black box" "box"}})
