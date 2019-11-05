(ns dangan-clj.views
  (:require [re-frame.core :as rf]))

(defn- action-label
  [action]
  (get {:action/talk    "Talk"
        :action/examine "Examine"} action "error"))

(defn action-dialog []
  (let [actions @(rf/subscribe [:actions])]
    [:div
     (for [action actions]
       [:button {:key      action
                 :type     "button"
                 :on-click #(rf/dispatch [:select-action action])}
        (action-label action)])]))

(defn text-dialog []
  (let [line @(rf/subscribe [:line])]
    [:div
     (or line
         [:p line])]))
