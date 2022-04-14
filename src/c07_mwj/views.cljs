(ns c07-mwj.views
  (:require
   [c07-mwj.charms :as charms]
   [c07-mwj.logic :as logic]))

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
    [:div
     [logo]
     [charms/charm
      {:id "citrus-cage"}
      [:div.koi-1
       [:span "Now in bloom"]
       [:article.glyph (str (char 4305))]]]
     [:pre
      {:style {:width 200
               :position "fixed"
               :left 10
               :bottom 40}}
      "xpos: " [:span#xpos ""]]
     [:pre
      {:style {:width 200
               :position "fixed"
               :left 210
               :bottom 40}}
      "ypos: " [:span#ypos ""]]
     [footer]]))
