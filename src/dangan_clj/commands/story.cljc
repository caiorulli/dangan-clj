(ns dangan-clj.commands.story
  (:require
   #?@(:clj  [[clojure.java.io :as io]]
       :cljs [[cljs.reader :refer [read-string]]]))
  #?(:cljs (:require-macros [dangan-clj.java.macros :refer [inline-resource]])))

(defn- load-story
  []
  #?(:clj  (read-string (slurp (io/resource "dev/story.edn")))
     :cljs (read-string (inline-resource "dev/story.edn"))))

(defn initialize
  []
  {:scene (load-story)
   :context #{}})
