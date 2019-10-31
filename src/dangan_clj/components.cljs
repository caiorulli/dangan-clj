(ns dangan-clj.components
  (:require [dangan-clj.entrypoint :as entrypoint]))

(defn text-dialog [game]
  (let [{:line/keys [speaker text] :as line} (entrypoint/line game)]
    [:div
     (when (seq line)
       [:p (or (and speaker
                    (str speaker ": " text))
               text)])]))
