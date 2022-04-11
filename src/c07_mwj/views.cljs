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
  (let [owner [:span#owner "frg"]]
    [:div.backmatter
     [:small>article#copyright (char 169) " " owner " " (logic/year)]]))

(defn main-panel []
  (let []
    [:div
     [logo]
     [charms/charm
      {:id "citrus-cage"}
      [:div.charm.no-select.hidden
       [:span "Now in bloom"]
       [:article (str (char 4305))]]]
     [footer]]))
