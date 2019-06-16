(ns dangan-clj.input.example)

(def rodrigo
  {:dialog-id :rodrigos-dead
   :scene-id :rodrigos-room})

(def phone
  {:dialog-id :rodrigos-phone
   :scene-id :rodrigos-room})

(def rodrigos-room
  {:display-name "Rodrigo's Room"
   :dialog-id :describe-rodrigos-room})

(def pool
  {:display-name "Pool"
   :dialog-id :describe-pool})

(def rodrigos-dead
  [[:giba   "What the hell? Rodrigo's dead?"]
   [:caio   "Now what, we call the police?"]
   [:thiago (str "We're still cut off from last night's storm.\n"
                 "        No phone signal, no internet.")]
   [:giba   (str "Yeah, even if we tried to get back to the city,\n"
                 "      the road's been blocked by falling trees.\n"
                 "      I guess we're stuck in Cesar's farmhouse.")]])

(def rodrigos-phone
  [[:caio (str "The phone still has its screen unlocked.\n"
               "    What could be so damn important that he would still hold on after dying...?")]
   [:giba "Oh, it's that series he's been watching, in which a woman goes back in time."]
   [:caio "So, whatever happened, might have happened while he was watching it?"]])

(def describe-rodrigos-room
  [[:thought "Rodrigo laid dead on his bed, pale and cold."]
   [:thought "No visible wound. He's still holding his phone."]
   [:thought "Me, Thiago and Giba stood around him, breathless, with no clue of how this came to happen."]
   [:thought "The others were still asleep, elsewhere."]])

(def describe-pool
  [[:thought "We're next to the pool, nice"]])

(def game
  {:first-scene :rodrigos-room
   :scenes      {:rodrigos-room rodrigos-room
                 :pool          pool}
   :pois {:rodrigo rodrigo
          :phone   phone}
   :dialogs {:rodrigos-dead rodrigos-dead
             :rodrigos-phone rodrigos-phone
             :describe-rodrigos-room describe-rodrigos-room
             :describe-pool describe-pool}
   :characters {:giba {:display-name "Giba"
                       :description  "A friend from college. He works for a man called \"The Little Fish\"."}
                :thiago {:display-name "Thiago"
                         :description "Very tall guy. They say if you tell him that, he'll give you a card."}
                :caio {:display-name "Me"
                       :description "Ignore this for now"}}})

(def cli-dict
  {:rodrigos-room #{"rodrigo's room" "room"}
   :pool          #{"pool" "pool area"}
   :rodrigo       #{"rodrigo" "batata"}
   :phone         #{"phone" "cell phone" "cell"}})
