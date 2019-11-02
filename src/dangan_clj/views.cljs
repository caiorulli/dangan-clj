(ns dangan-clj.views
  (:require [re-frame.core :as rf]))

(defn text-dialog []
  (let [line @(rf/subscribe [:line])]
    [:div
     (or line
         [:p line])]))
