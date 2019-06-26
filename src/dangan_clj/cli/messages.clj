(ns dangan-clj.cli.messages)

(defn clue-description [clue]
  (str (:display-name clue) " - "
       (:description clue) "\n"))

(defn list-clues-text [cli game]
  (let [clue-ids (-> cli :player :clues)
        clues (map #(get (:clues game) %) clue-ids)]
    (str "Clues in possession:\n\n"
         (if (empty? clues)
           "You don't have any clues yet.\n"
           (apply str (map clue-description clues))))))

(defn help-text [_ _]
  (str "Command list:\n\n"
       "help                 Displays this text.\n"
       "describe             Describes the current scene.\n"
       "examine (something)  Triggers events related to examination of points of interest or people on the current scene.\n"
       "talk to (person)     Starts conversation with person on the current scene.\n"
       "go to (scene)        Goes to another place, accessible through the current scene.\n"))

(def welcome-text
  (str "\nWelcome to the demo game for dangan-clj.\n"
       "In case of need, type \"help\".\n"))

(def text-dictionary
  {:help help-text
   :list-clues list-clues-text})

(defn simple-text [cli game]
  ((get text-dictionary (:simple-text cli)) cli game))
