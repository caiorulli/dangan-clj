(ns dangan-clj.java.macros
  (:require [clojure.java.io :as io]))

(defmacro inline-resource
  "Source: https://clojureverse.org/t/best-practices-for-importing-raw-text-files-into-clojurescript-projects/2569"
  [resource-path]
  (slurp (io/resource resource-path)))
