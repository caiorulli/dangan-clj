(ns dangan-clj.cli.messages)

(def help-text
  (str "Command list:\n\n"
       "help                 Displays this text.\n"
       "describe             Describes the current scene.\n"
       "examine (something)  Triggers events related to examination of points of interest or people on the current scene.\n"
       "talk to (person)     Starts conversation with person on the current scene.\n"
       "go to (scene)        Goes to another place, accessible through the current scene.\n"))

(def welcome-text
  (str "\nWelcome to the demo game for dangan-clj.\n"
       "In case of need, type \"help\".\n"))
