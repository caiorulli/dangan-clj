(ns dangan-clj.game.example)

(def clue-1 {:id 1})

(def knife
  {:name "knife"
   :clue clue-1
   :dialog [{:speaker "Giba"
             :text    "That's the knife I used to cut tomatoes."}]})

(def schredder
  {:name "schredder"
   :dialog [{:speaker "Thiago"
              :text    "What's that big weird machine?"}
             {:speaker "Giba"
              :text    "It's a paper schredder."}
             {:speaker "Thiago"
              :text    "Why do you even have that here?"}]})

(def test-scenes
  {:name "Giba's House"
   :description (str "We're in Giba's appartment for the first time.\n"
                     "It seems very organized and clean.\n"
                     "There is a distinctive, disturbing feel to it, though.\n\n"
                     "We could see a red-tainted knife in the counter and a big black box on a shelf. Is that a schredder?\n")
   :pois #{knife
            schredder}})
