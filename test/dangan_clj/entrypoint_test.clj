(ns dangan-clj.entrypoint-test
  (:require [dangan-clj.entrypoint :as entrypoint]
            [midje.sweet :refer [=> fact facts]]
            [dangan-clj.input.test-game :as test-game]
            [dangan-clj.cli.cli :as cli]))

(fact "Initial game"
  (let [{:game/keys [log cli content]} (entrypoint/init test-game/test-game)]
    log => []
    cli => (cli/cli test-game/test-game)
    content => test-game/test-game))

(facts "Line query"
  (fact "No current-dialog and current-line properties"
    (let [game {:game/cli     {}
                :game/content {:dialogs    {:lala [[:dr-gori "Pare!"]
                                                   [:dr-gori "Não deixarei..."]]}
                               :characters {:dr-gori {:display-name "Dr. Gori"}}}}]
      (entrypoint/line game) => nil))

  (fact "Existing line"
    (let [game {:game/cli     {:current-dialog :lala
                               :current-line   1}
                :game/content {:dialogs    {:lala [[:dr-gori "Pare!"]
                                                   [:dr-gori "Não deixarei..."]]}
                               :characters {:dr-gori {:display-name "Dr. Gori"}}}}]
      (entrypoint/line game) => {:line/speaker "Dr. Gori"
                                 :line/text    "Não deixarei..."})))
