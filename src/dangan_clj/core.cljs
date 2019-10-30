(ns ^:figwheel-hooks dangan-clj.core
  (:require [goog.dom :as gdom]
            [reagent.core :as reagent :refer [atom]]))

(defn multiply
  "Placeholder test function"
  [a b]
  (* a b))

(defn get-app-element []
  (gdom/getElement "app"))

(defn hello-world []
  [:div "Hello Dangan-clj!"])

(defn mount [el]
  (reagent/render-component [hello-world] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
