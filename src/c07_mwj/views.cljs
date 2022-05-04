(ns c07-mwj.views
  (:require
   [c07-mwj.charms :as charms]
   [c07-mwj.logic :as logic]
   [c07-mwj.assets :as assets]
   ; [c07-mwj.utilities :as util]
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
; (defn mailer
;   []
;   [charms/charm
;    {:id "mailer"
;     :container
;     [:div.koi-2
;      [:svg
;       {:viewBox "0 0 24 24"
;        :width "156"
;        :style {:fill "lemonchiffon"}}
;       [:path {:d assets/mail-svg}]]
;      [:span "Mailer"]]
;     :contains
;     [[:div
;       {:style {:display "block"
;                :margin "0 auto"
;                :width "max-content"}}
;       [util/form-loader "5fd5468ccdc040ff3b12248e"]]]}])

; (defn newt
;   []
;   [charms/charm
;    {:id "newt"
;     :container
;     [:div.koi-1
;      [:span "Now this"]
;      [:article.glyph "0"]]}])

; (defn citrus
;   []
;   [charms/charm
;    {:id "citrus"
;     :container
;     [:div.koi-1
;      [:article.glyph (char 4305)]
;      [:span "Now in bloom"]]}])

(defn bottle
  []
  [charms/charm
   {:id "bottle"
    :container
    [:div.koi-3
     [:div
      [:img {:src "/_asset/bottle.svg"
             :alt "A couplet: A well contained emptiness, empty kept, your beautiful bottle, shared from the top."
             :draggable false
             :width 180
             :height 265}]]]}])

(defn mail
  []
  [charms/charm
   {:id "mail"
    :container
    [:div.koi-4
     [:img {:src "/_asset/mail.gif"
            :alt "A gif: You'll Get Mail"
            :draggable false
            :width 54
            :height 63}]]}])

(defn bloom
  []
  [charms/charm
   {:id "blue"
    :container
    [:div.koi-5
     [:span "Blooming"]
     [:div.trigram
      [:table>tbody
       [:tr [:td] [:td.split] [:td]]
       [:tr [:td] [:td] [:td]]
       [:tr [:td] [:td] [:td]]
       [:tr [:td {:colspan 3}]]]]]}])

;; ========== PANELS ===========================================================
(defn main-panel []
  [:<>
   [logo]
   ; [mailer]
   ; [newt]
   ; [citrus]
   [bottle]
   [mail]
   [bloom]
   ; DEBUG
   #_[debug/pointer-elements]
   #_[debug/xy-elements]
   [footer]])
