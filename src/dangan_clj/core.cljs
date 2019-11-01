(ns ^:figwheel-hooks dangan-clj.core
  (:require [dangan-clj.components :as components]
            [dangan-clj.entrypoint :as entrypoint]
            [dangan-clj.input.example :as example]
            [goog.dom :as gdom]
            [re-frame.core :as rf]
            [reagent.core :as reagent :refer [atom]]))

(rf/reg-event-db
  :initialize
  (fn [_ _]
    {:input ""
     :game  (entrypoint/init example/game example/cli-dict)}))

(rf/reg-event-db
  :change-input
  (fn [db [_ new-input]]
    (assoc db :input new-input)))

(defn command-result
  [{:keys [input game]}]
  (let [new-game (entrypoint/exec game input)]
    {:input ""
     :game  new-game}))

(rf/reg-event-db
  :execute-input
  (fn [db _]
    (command-result db)))

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

(defn get-app-element []
  (gdom/getElement "app"))

(defn hello-world []
  [:div
   "Hello Dangan-clj!"
   [:input {:type      "text"
            :value     @(rf/subscribe [:input])
            :on-change #(rf/dispatch [:change-input (-> % .-target .-value)])}]
   [:button {:type     "button"
             :on-click #(rf/dispatch [:execute-input])} "Execute"]
   [:br]
   [:br]
   [components/text-dialog]])

(defn mount [el]
  (reagent/render-component [hello-world] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(rf/dispatch-sync [:initialize])

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
