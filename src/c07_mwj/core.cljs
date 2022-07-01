(ns c07-mwj.core
  (:require
   [c07-mwj.views :as views]
   [c07-mwj.config :as config]
   [c07-mwj.utilities :as utils]
   [c07-mwj.rf :as rf :refer [<sub >evt]]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [ajax.core :as ajax]
   [ajax.edn :as edn]
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
                    #js{:bounds "#canvas"
                        :onClick #(js/console.log (char 9786))}))

(comment
  (set! (.. (js/document.getElementById "canvas") -style -visibility) "visible")
  )

;; ========== DATA REQUESTS ====================================================
(defn load!
  [source]
  (ajax/GET source
    {:response-format (edn/edn-response-format)
     :handler #(>evt [::rf/load-charm-list %])
     :error-handler #(println "The request failed. Response:" %)
     :finally #(>evt [::rf/temporary])}))

(defn init []
  (load! "/_data/charms.edn")
  (re-frame/dispatch-sync [::rf/initialize-db])
  (dev-setup)
  (mount-root)
  (js/console.info "all loaded up"))
