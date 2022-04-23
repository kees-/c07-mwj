(ns c07-mwj.views
  (:require
   [c07-mwj.charms :as charms]
   [c07-mwj.logic :as logic]
   [c07-mwj.assets :as assets]
   [c07-mwj.utilities :as util]
   [c07-mwj.debug :as debug]))

(defn logo
  []
  [:a.logo-container
   {:href "/"}
   [:article.logo "MWJ"]])

(defn footer
  []
  (let [owner [:span#owner "FRG LLC"]]
    [:div.backmatter
     [:small>article#copyright (char 169) " " owner " " (logic/year)]]))

(defn main-panel []
  (let []
    [:<>
     [logo]
     [charms/charm
      {:id "citrus-cage"}
      [:div.koi-2
       [:svg
        {:viewBox "0 0 24 24"
         :width "156"
         :style {:fill "lemonchiffon"}}
        [:path {:d assets/mail-svg}]]
       [:span "Mailer"]]]
     [charms/charm
      {:id "newt"}
      [:div.koi-1
       [:span "Now this"]
       [:article.glyph "2"]]]
     ; DEBUG
     #_[debug/pointer-elements]
     #_[debug/xy-elements]
     [footer]]))
