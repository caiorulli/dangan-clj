(ns dangan-clj.core
  (:gen-class))

(defn make-player []
  {:clues #{}})

(defn make-poi [name clue]
  {:name name :clue clue})

(defn interact [player poi]
  (assoc player :clues
         (conj (:clues player) (:clue poi))))

(defn interact-with [poi-name player scene]
  (interact player
            (first (clojure.set/select
                    #(= (:name %) poi-name)
                    (:pois scene)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
