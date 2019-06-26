(ns dangan-clj.input.example)

(def rodrigo
  {:dialog-id :rodrigos-dead
   :scene-id :rodrigos-room})

(def phone
  {:dialog-id :rodrigos-phone
   :scene-id :rodrigos-room
   :clue-id :rodrigos-phone})

(def rodrigos-room
  {:display-name "Rodrigo's Room"
   :dialog-id :describe-rodrigos-room
   :presences [[:giba :talk-to-giba]
               [:thiago :describe-thiago]]})

(def pool
  {:display-name "Pool"
   :dialog-id :describe-pool
   :presences []})

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

(def describe-giba
  [[:thought "A friend from college."]
   [:thought "He works for a man called \"The Little Fish\"."]])

(def talk-to-giba
  [[:giba    "What's the cause of death? He looks normal."]
   [:caio    "Hell if I know."]
   [:giba    "Maybe he just watched too much anime for a lifetime."]
   [:giba    "There's a very good anime film in theaters now..."]
   [:giba    "It's about a guy who learns as he fights."]])

(def describe-thiago
  [[:thought "Very tall guy."]
   [:thought "They say if you tell him that, he'll give you a card."]])

(def describe-me
  [[:thought "Ignore this for now."]])

(def clues
  {:rodrigos-phone {:display-name "Rodrigo's Phone"
                    :description "He was still holding the phone when he died."}})

(def game
  {:first-scene :rodrigos-room
   :scenes      {:rodrigos-room rodrigos-room
                 :pool          pool}
   :pois {:rodrigo rodrigo
          :phone   phone}
   :dialogs {:rodrigos-dead rodrigos-dead
             :rodrigos-phone rodrigos-phone
             :describe-rodrigos-room describe-rodrigos-room
             :describe-pool describe-pool
             :describe-giba describe-giba
             :describe-thiago describe-thiago
             :describe-me describe-me
             :talk-to-giba talk-to-giba}
   :characters {:giba {:display-name "Giba"
                       :dialog-id :describe-giba}
                :thiago {:display-name "Thiago"
                         :dialog-id :describe-thiago}
                :caio {:display-name "Me"
                       :dialog-id :describe-me}}
   :clues clues})

(def cli-dict
  {:rodrigos-room #{"rodrigo's room" "room"}
   :pool          #{"pool" "pool area"}
   :rodrigo       #{"rodrigo" "batata"}
   :phone         #{"phone" "cell phone" "cell"}
   :giba          #{"giba" "gilberto" "guilherme"}
   :thiago        #{"thiago" "thits"}})
