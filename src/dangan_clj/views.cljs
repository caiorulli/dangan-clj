(ns dangan-clj.views
  (:require [re-frame.core :as rf]))

(defn action-dialog []
  (let [actions @(rf/subscribe [:actions])]
    [:div
     (for [action actions]
       [:button {:type "button"} action])]))

(defn text-dialog []
  (let [line @(rf/subscribe [:line])]
    [:div
     (or line
         [:p line])]))
