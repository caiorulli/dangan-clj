(ns dangan-clj.game.example)

(def rodrigo
  {:name "rodrigo"
   :dialog [{:speaker "Giba"
             :text    "What the hell? Rodrigo's dead?"}]})

(def phone
  {:name "phone"
   :dialog [{:speaker "Me"
             :text    (str "The phone still has its screen unlocked.\n"
                           "    What could be so damn important that he would still hold after dying...?")}
            {:speaker "Giba"
             :text    "Oh, it's that series he's been watching, in which a woman goes back in time."}
            {:speaker "Me"
             :text    "So, whatever happened, might have happened while he was watching it?"}]})

(def rodrigos-room
  {:id          :rodrigos-room
   :name        "Rodrigo's Room"
   :description (str "Rodrigo laid dead on his bed, choked on blood.\n"
                     "No visible wound. He's still holding his phone.\n"
                     "Me, Thiago and Giba stood around him, breathless, with no clue of how this came to happen.\n"
                     "The others were still asleep, elsewhere.\n")
   :pois        #{rodrigo
                  phone}})

(def arandu-game
  {:first-scene (:id rodrigos-room)
   :scenes      #{rodrigos-room}})
