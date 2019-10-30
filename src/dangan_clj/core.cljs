(ns ^:figwheel-hooks dangan-clj.core
  (:require [dangan-clj.entrypoint :as entrypoint]
            [dangan-clj.input.example :as example]
            [goog.dom :as gdom]
            [reagent.core :as reagent :refer [atom]]))

(defonce app-state (atom {:input ""
                          :game  (entrypoint/init example/game example/cli-dict)}))

(defn command-result
  [{:keys [input game]}]
  (let [new-game (entrypoint/exec input example/game example/cli-dict game)]
    {:input ""
     :game  new-game}))

(defn get-app-element []
  (gdom/getElement "app"))

(defn hello-world []
  (prn @app-state)
  [:div
   "Hello Dangan-clj!"
   [:input {:type      "text"
            :value     (:input @app-state)
            :on-change #(swap! app-state assoc :input (-> % .-target .-value))}]
   [:button {:type     "button"
             :on-click #(reset! app-state (command-result @app-state))}
    "Execute"]
   [:br]
   [:br]
   (for [line (-> @app-state :game :output)]
     [:p line])])

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
