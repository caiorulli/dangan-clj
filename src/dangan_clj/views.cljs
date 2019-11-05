(ns dangan-clj.views
  (:require [re-frame.core :as rf]))

(def actions
  #{:action/talk :action/examine})

(defn- action-label
  [action]
  (if (contains? actions action)
    (get {:action/talk    "Talk"
          :action/examine "Examine"} action "error")
    action))

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
