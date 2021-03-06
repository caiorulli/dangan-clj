(ns ^:figwheel-hooks dangan-clj.core
  (:require [dangan-clj.views :as views]
            [dangan-clj.events]
            [dangan-clj.subs]
            [goog.dom :as gdom]
            [re-frame.core :as rf]
            [reagent.core :as reagent :refer [atom]]))

(defn get-app-element []
  (gdom/getElement "app"))

(defn app []
  [:div
   [:br]
   [views/action-dialog]
   [:br]
   [views/text-dialog]
   [:br]
   [:div (str @(rf/subscribe [:log]))]])

(defn mount [el]
  (reagent/render-component [app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(rf/dispatch-sync [:initialize])

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (rf/clear-subscription-cache!)
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
