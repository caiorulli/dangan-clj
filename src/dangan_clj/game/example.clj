(ns dangan-clj.game.example)

(def rodrigo
  {:id :rodrigo
   :name "rodrigo"
   :dialog [{:speaker "Giba"
             :text    "What the hell? Rodrigo's dead?"}
            {:speaker "Me"
             :text    "Now what, we call the police?"}
            {:speaker "Thiago"
             :text    (str "We're still cut off from last night's storm.\n"
                           "        No phone signal, no internet.")}
            {:speaker "Giba"
             :text    (str "Yeah, even if we tried to get back to the city,\n"
                           "      the road's been blocked by falling trees.\n"
                           "      I guess we're stuck in Cesar's farmhouse.")}]})

(def phone
  {:id :phone
   :name "phone"
   :dialog [{:speaker "Me"
             :text    (str "The phone still has its screen unlocked.\n"
                           "    What could be so damn important that he would still hold on after dying...?")}
            {:speaker "Giba"
             :text    "Oh, it's that series he's been watching, in which a woman goes back in time."}
            {:speaker "Me"
             :text    "So, whatever happened, might have happened while he was watching it?"}]})

(def rodrigos-room
  {:id           :rodrigos-room
   :display-name "Rodrigo's Room"
   :name         "Rodrigo's Room"
   :synonyms     ["rodrigo's room" "room"]
   :description  (str "Rodrigo laid dead on his bed, pale and cold.\n"
                     "No visible wound. He's still holding his phone.\n"
                     "Me, Thiago and Giba stood around him, breathless, with no clue of how this came to happen.\n"
                     "The others were still asleep, elsewhere.\n")
   :pois         #{rodrigo
                  phone}})

(def pool
  {:id           :pool
   :display-name "Pool"
   :name         "Pool"
   :synonyms     ["pool" "pool area"]
   :description  "We're next to the pool, nice"
   :pois         #{}})

(def arandu-game
  {:first-scene (:id rodrigos-room)
   :scenes      #{rodrigos-room
                  pool}})
