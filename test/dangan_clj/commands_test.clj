(ns dangan-clj.commands-test
  (:require [dangan-clj.commands :as commands]
            [midje.sweet :refer [=> fact]]))

(fact "Commands should be straightforward factories"
  (commands/describe) => #:command {:type :describe}
  (commands/examine :tinkywinky) => #:command {:type   :examine
                                               :target :tinkywinky}
  (commands/navigate :mutenroshi-island) => #:command {:type   :navigate
                                                       :target :mutenroshi-island}
  (commands/talk :piccolo) => #:command {:type   :talk
                                         :target :piccolo}
  (commands/list-clues) => #:command {:type :list-clues})
