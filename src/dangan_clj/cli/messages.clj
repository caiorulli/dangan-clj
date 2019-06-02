(ns dangan-clj.cli.messages)

(def help-text
  (str "Command list:\n\n"
       "help              Displays this text.\n"
       "describe          Describes the current scene.\n"
       "examine (object)  Triggers events related to examination of points of interest.\n"))

(def welcome-text
  (str "\nWelcome to the demo game for dangan-clj.\n"
       "In case of need, type \"help\".\n"))
