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
  (reagent/create-class
   {:reagent-render
    (fn []
      [:div#citrus-cage.no-select
       [:span "Now in bloom"]
       [:article (str (char 4305))]])
    :component-did-mount
    (fn []
      (let [el (js/document.getElementById "citrus-cage")
            opts (clj->js {})
            move (fn [element x y]
                   (set! (.. element -style -transform)
                     (str "translate(" x "px, " y "px)")))]
        (contact/PointerListener. el opts)
        (.addEventListener el "panend"
         (fn [e]
           (move el 0 0)))
        (.addEventListener el "pan"
         (fn [e]
           (move el
                 (.. e -detail -global -deltaX)
                 (.. e -detail -global -deltaY))))))}))

(defn main-panel []
  (let []
    [:div
     [logo]
     [citrus]
     [footer]]))
