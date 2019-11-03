(ns dangan-clj.commands.story
  (:require
   #?@(:clj  [[clojure.java.io :as io]]
       :cljs [[cljs.reader :refer [read-string]]]))
  #?(:cljs (:require-macros [dangan-clj.java.macros :refer [inline-resource]])))

(defn load-story
  "Loads story available in resources"
  []
  #?(:clj  (read-string (slurp (io/resource "test/story.edn")))
     :cljs (read-string (inline-resource "test/story.edn"))))
