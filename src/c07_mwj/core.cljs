(ns c07-mwj.core
  (:require
   [c07-mwj.views :as views]
   [c07-mwj.config :as config]
   [c07-mwj.rf :as rf]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  ; (re-frame/dispatch-sync [::rf/initialize-db])
  (dev-setup)
  (mount-root))
