(ns dangan-clj.input.test-game)

(def clue-1 {:id 1})

(def knife
  {:clue     clue-1
   :dialog-id :knife-dialog
   :scene-id :gibas-room})

(def schredder
  {:dialog-id :schredder-dialog
   :scene-id :gibas-room})

(def gibas-room
  {:display-name "Giba's Room"
   :dialog-id :describe-gibas-room})

(def washing-machine
  {:dialog-id :washing-machine-dialog
   :scene-id :laundry})

(def laundry
  {:display-name "Laundry"
   :dialog-id :describe-laundry})

(def knife-dialog
  [[:giba "That's the knife I used to cut tomatoes."]])

(def schredder-dialog
  [[:thiago "What's that big weird machine?"]
   [:giba   "It's a paper schredder."]
   [:thiago "Why do you even have that here?"]])

(def washing-machine-dialog
  [[:thiago "So here's where you hide the bodies."]])

(def describe-gibas-room
  [[:thought "Giba's hauntingly neat and organized room."]])

(def describe-laundry
  [[:thought "So this is where Giba disposes of blood-soaked clothes..."]])

(def describe-giba
  [[:thought "A respectable gentleman"]])

(def describe-thiago
  [[:thought "A very tall gentleman"]])

(def test-game
  {:scenes {:gibas-room gibas-room
            :laundry    laundry}
   :first-scene :gibas-room
   :pois   {:knife knife
            :schredder schredder
            :washing-machine washing-machine}
   :dialogs {:knife-dialog knife-dialog
             :schredder-dialog schredder-dialog
             :washing-machine-dialog washing-machine-dialog
             :describe-gibas-room describe-gibas-room
             :describe-laundry describe-laundry
             :describe-giba describe-giba
             :describe-thiago describe-thiago}
   :characters {:giba {:display-name "Giba"
                       :dialog-id :describe-giba}
                :thiago {:display-name "Thiago"
                         :dialog-id :describe-thiago}}})

(def cli-dict
  {:gibas-room #{"room" "giba's room"}
   :laundry    #{"laundry" "laundry area"}
   :knife      #{"knife"}
   :schredder  #{"schredder" "black box" "box"}
   :washing-machine #{"washing-machine"}})
