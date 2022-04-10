(ns c07-mwj.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [c07-mwj.subs :as subs]
   ["contactjs" :as contact]))

(defn logo
  []
  [:a.logo-container
   {:href "/"}
   [:article.logo "MWJ"]])

(defn footer
  []
  (let [year (.getFullYear (js/Date.))
        owner [:span#owner "frg"]]
    [:div.backmatter
     [:small>article#copyright (char 169) " " owner " " year]]))

(defn citrus
  []
  [:div.citrus-cage
   [:span "Now in bloom"]
   [:article (str (char 4305))]])

(defn main-panel []
  (let []
    [:div
     [logo]
     [citrus]
     [footer]]))
