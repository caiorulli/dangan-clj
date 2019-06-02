(ns dangan-clj.input.example)

(def rodrigo
  {:dialog [{:speaker "Giba"
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
  {:dialog [{:speaker "Me"
             :text    (str "The phone still has its screen unlocked.\n"
                           "    What could be so damn important that he would still hold on after dying...?")}
            {:speaker "Giba"
             :text    "Oh, it's that series he's been watching, in which a woman goes back in time."}
            {:speaker "Me"
             :text    "So, whatever happened, might have happened while he was watching it?"}]})

(def rodrigos-room
  {:display-name "Rodrigo's Room"
   :description  (str "Rodrigo laid dead on his bed, pale and cold.\n"
                     "No visible wound. He's still holding his phone.\n"
                     "Me, Thiago and Giba stood around him, breathless, with no clue of how this came to happen.\n"
                     "The others were still asleep, elsewhere.\n")
   :pois         {:rodrigo rodrigo
                  :phone   phone}})

(def pool
  {:display-name "Pool"
   :description  "We're next to the pool, nice"
   :pois         #{}})

(def game
  {:first-scene :rodrigos-room
   :scenes      {:rodrigos-room rodrigos-room
                 :pool          pool}})

(def cli-dict
  {:rodrigos-room #{"rodrigo's room" "room"}
   :pool          #{"pool" "pool area"}
   :rodrigo       #{"rodrigo" "batata"}
   :phone         #{"phone" "cell phone" "cell"}})
