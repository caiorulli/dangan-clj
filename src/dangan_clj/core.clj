(ns dangan-clj.core
  (:gen-class))

(defn- make-player []
  {:clues #{}})

(defn make-poi [name clue]
  {:name name :clue clue})

(defn make-initial-state [scene]
  {:player (make-player)
   :scene scene})

(defn- interact [player poi]
  (if (nil? poi)
    player
    (assoc player :clues
           (conj (:clues player) (:clue poi)))))

(defn interact-with [poi-name state]
  (assoc state :player
         (interact (:player state)
                   (first (clojure.set/select
                           #(= (:name %) poi-name)
                           (:pois (:scene state)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
