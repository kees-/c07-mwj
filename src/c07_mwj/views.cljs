(ns c07-mwj.views
  (:require
   [c07-mwj.charms :as charms]
   [c07-mwj.logic :as logic]
   [c07-mwj.rf :as rf :refer [<sub >evt]]
   [reagent.core :as reagent]
   [c07-mwj.debug :as debug]))

;; ========== CONTENTS =========================================================
(defn logo
  []
  [:a.logo-container
   {:href "/"}
   [:article.logo "MWJ"]])

(defn ph-number
  []
  [:a.ph
   {:href "tel:+13108481990"}
   [:article "+1 310 848 1990"]])

(defn footer
  []
  (let [owner [:span#owner "FRG LLC"]]
    [:div.backmatter
     [:small>article#copyright (char 169) " " owner " " (logic/year)]]))

;; ========== PANELS ===========================================================
(defn canvas
  []
  (into
   [:div#canvas
    {:style {:visibility "visible"}}]
   (map
    (fn [id] [charms/rf-charm id])
    (<sub [::rf/current-charms "canvas"]))))

(defn main-panel
  []
  [:<>
   [logo]
   [ph-number]
   [canvas]
   [footer]])
