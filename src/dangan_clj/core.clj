(ns dangan-clj.core
  (:gen-class))

(defn- make-player []
  {:clues #{}})

(defn make-poi [name clue]
  {:name name :clue clue})

(defn make-initial-state [scene]
  {:player (make-player)
   :scene scene})

(defn- find-poi [poi-name state]
  (first (clojure.set/select
          #(= (:name %) poi-name)
          (:pois (:scene state)))))

(defn- interact [player poi]
  (assoc player :clues
         (conj (:clues player) (:clue poi))))

(defn interact-with [poi-name state]
  (let [poi (find-poi poi-name state)]
    (if (nil? poi)
      state
      (merge state
             {:player (interact (:player state) poi)
              :mode :dialog}))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
