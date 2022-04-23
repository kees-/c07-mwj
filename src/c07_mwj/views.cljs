(ns c07-mwj.views
  (:require
   [c07-mwj.charms :as charms]
   [c07-mwj.logic :as logic]
   [c07-mwj.assets :as assets]
   [c07-mwj.utilities :as util]
   [c07-mwj.debug :as debug]))

;; ========== CONTENTS =========================================================
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

;; ========== CHARMS ===========================================================
(defn mailer
  []
  [charms/charm
   {:id "mailer"
    :container
    [:div.koi-2
     [:svg
      {:viewBox "0 0 24 24"
       :width "156"
       :style {:fill "lemonchiffon"}}
      [:path {:d assets/mail-svg}]]
     [:span "Mailer"]]
    :contains
    [[:div
      {:style {:display "block"
               :margin "0 auto"
               :width "max-content"}}
      [util/form-loader "5fd5468ccdc040ff3b12248e"]]]}])

(defn newt
  []
  [charms/charm
   {:id "newt"
    :container
    [:div.koi-1
     [:span "Now this"]
     [:article.glyph "0"]]}])

;; ========== PANELS ===========================================================
(defn main-panel []
  [:<>
   [logo]
   [mailer]
   [newt]
   ; DEBUG
   #_[debug/pointer-elements]
   #_[debug/xy-elements]
   [footer]])

(comment
 [:div
  {:id "flodesk"
   :style {:display "block"
           :margin "0 auto"
           :width "max-content"}}
  [util/form-loader "5fd5468ccdc040ff3b12248e"]])
