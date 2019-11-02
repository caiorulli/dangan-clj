(ns dangan-clj.subs
  (:require [dangan-clj.entrypoint :as entrypoint]
            [re-frame.core :as rf]))

(rf/reg-sub
  :input
  (fn [db _]
    (:input db)))

(rf/reg-sub
  :line
  (fn [db _]
    (let [{:line/keys [speaker text] :as line} (entrypoint/line (:game db))]
      (when (seq line)
        (or (and speaker
                 (str speaker ": " text))
            text)))))
