(ns dangan-clj.cli.command
  (:require [clojure.spec.alpha :as s]))

(s/def ::type #{:describe :examine :help})
(s/def ::target keyword?)

(s/def ::command (s/keys :req-un [::type]
                         :opt-un [::target]))
