(ns dangan-clj.commands)

(defn describe []
  #:command {:type :describe})

(defn examine [target]
  #:command {:type   :examine
             :target target})

(defn navigate [target]
  #:command {:type   :navigate
             :target target})

(defn talk [target]
  #:command {:type   :talk
             :target target})

(defn list-clues []
  #:command {:type :list-clues})
