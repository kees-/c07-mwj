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

; (defn bottle
;   []
;   [charms/charm
;    {:id "bottle"
;     :container
;     [:div.koi-3
;      [:div
;       [:img {:src "/_asset/bottle.svg"
;              :alt "A couplet: A well contained emptiness, empty kept, your beautiful bottle, shared from the top."
;              :draggable false
;              :width 180
;              :height 265}]]]}])

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

; (defn bloom
;   []
;   [charms/charm
;    {:id "blue"
;     :container
;     [:div.koi-5
;      [:span "Settling"]
;      [:div.trigram.trigram2
;       [:table>tbody
;        [:tr [:td] [:td.split] [:td]]
;        [:tr [:td] [:td] [:td]]
;        [:tr [:td] [:td.split] [:td]]
;        [:tr [:td {:colspan 3}]]]]]}])

(defn leaf-1
  []
  [charms/charm
   {:id "leaves"
    :container
    [:div
     {:style {:padding "1rem .5rem"
              :background "black"
              :border "2px solid #262626"}}
     [:div
      {:style {:padding ".5rem"
               :background "#1a1a1a"
               :border "2px groove #4d4d4d"}}
      [:div
       {:style {:padding ".2rem"
                :background "#262626"
                :border "1px groove silver"}}
       [:video
        {:src "/_asset/leaf-1.webm"
         :type "video/webm"
         :width 135
         :height 135
         :autoplay ""
         :loop "loop"
         :muted "muted"
         :playsinline ""}]]]]}])

(defn leaf-2
  []
  [charms/charm
   {:id "leaf-2"
    :container
    [:div
     {:style {:border "2px outset #004D4D"
              :border-radius 1
              :padding ".4rem"
              :background "#033"}}
     [:div
      {:style {:border "3px inset teal"
               :padding ".25rem"
               :background "lightgrey"}}
      [:img
       {:src "/_asset/leaf-2.png"
        :height 60}]]]}])

(defn leaf-3
  []
  [charms/charm
   {:id "leaf-2"
    :container
    [:div
     {:style {:border "2px outset sienna"
              :border-radius 1
              :padding ".4rem"
              :background "#c9641d"}}
     [:div
      {:style {:border "3px inset darksalmon"
               :padding ".25rem"
               :background "wheat"}}
      [:img
       {:src "/_asset/leaf-3.png"
        :width 50}]]]}])

;; ========== PANELS ===========================================================
(defn main-panel []
  [:<>
   [logo]
   [ph-number]
   ; [mailer]
   ; [newt]
   ; [citrus]
   ; [bottle]
   ; [bloom]
   [mail]
   [leaf-1]
   #_[charms/rf-charm "leaf-1"]
   [leaf-2]
   [leaf-3]
   ; DEBUG
   #_[debug/pointer-elements]
   #_[debug/xy-elements]
   [footer]])
