(ns c07-mwj.core
  (:require
   [c07-mwj.views :as views]
   [c07-mwj.config :as config]
   [c07-mwj.utilities :as utils]
   [c07-mwj.rf :as rf]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   ["gsap$gsap" :as gsap]
   ["gsap/Draggable$Draggable" :as Draggable]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  ; Look!
  (gsap/registerPlugin Draggable)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el))
  ; Look!
  (Draggable/create ".charm"
   #js{:bounds "#app"
       :onClick #(js/console.log (char 9786))}))

(defn init []
  (re-frame/dispatch-sync [::rf/initialize-db])
  (utils/load! "/_data/charms.edn")
  (dev-setup)
  (mount-root))
