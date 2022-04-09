(ns c07-mwj.views
  (:require
   [re-frame.core :as re-frame]
   [c07-mwj.subs :as subs]))

(defn logo
  []
  [:a.logo-container
   {:href "/"}
   [:article.logo "MWJ"]])

(defn citrus
  []
  [:div.citrus-cage
   [:span "Now in bloom"]
   [:article (str (char 4305))]])

(defn main-panel []
  (let []
    [:div
     [logo]
     [citrus]]))
