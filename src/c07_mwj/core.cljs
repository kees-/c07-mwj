(ns c07-mwj.core
  (:require
   [c07-mwj.views :as views]
   [c07-mwj.config :as config]
   [c07-mwj.re-frame :as rf]
   [reagent.dom :as rdom]
   [re-frame.core :as rf]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (rf/dispatch-sync [::rf/initialize-db])
  (dev-setup)
  (mount-root))
