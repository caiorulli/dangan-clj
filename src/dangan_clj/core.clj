(ns dangan-clj.core
  (:gen-class))

(defn- make-player []
  {:clues #{}})

(defn make-poi [name clue dialog]
  {:name name :clue clue :dialog dialog})

(defn make-initial-state [scene]
  {:player (make-player)
   :scene scene
   :mode :interact})

(defn- find-poi [poi-name state]
  (first (clojure.set/select
          #(= (:name %) poi-name)
          (:pois (:scene state)))))

(defn- add-clue [player poi]
  (assoc player :clues
         (conj (:clues player) (:clue poi))))

(defn interact-with [poi-name state]
  (let [poi (find-poi poi-name state)]
    (if (nil? poi)
      state
      (merge state
             {:player (add-clue (:player state) poi)
              :mode :dialog
              :text (:text (first (:dialog poi)))
              :speaker (:speaker (first (:dialog poi)))}))))

(defn advance-dialog [state]
  (merge state
         {:mode :interact
          :speaker nil
          :text nil}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
