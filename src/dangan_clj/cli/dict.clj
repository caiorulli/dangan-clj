(ns dangan-clj.cli.dict
  (:require [clojure.spec.alpha :as s]))

(s/def ::cli-dict (s/map-of keyword? (s/coll-of string?)))

(defn lookup
  [cli-dict predicate]
  (first (filter #(some (partial = predicate)
                        (get cli-dict %))
                 (keys cli-dict))))
