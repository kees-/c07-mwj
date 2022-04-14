(ns c07-mwj.debug
  (:require
   [oops.core :refer [oset! oget]]))

(defn pointer-elements
  []
  [:<>
   [:pre
    {:style {:width 200
             :position "fixed"
             :left 10
             :bottom 90}}
    "actv? " [:span#actv]]
   [:pre
    {:style {:width 200
             :position "fixed"
             :left 10
             :bottom 80}}
    "xpnt: " [:span#xpnt]]
   [:pre
    {:style {:width 200
             :position "fixed"
             :left 10
             :bottom 70}}
    "ypnt: " [:span#ypnt]]
   [:pre
    {:style {:width 200
             :position "fixed"
             :left 10
             :bottom 60}}
    "e-∆x: " [:span#dltx]]
   [:pre
    {:style {:width 200
             :position "fixed"
             :left 10
             :bottom 50}}
    "e-∆y: " [:span#dlty]]])

(defn xy-elements
  []
  [:<>
   [:pre
    {:style {:width 200
             :position "fixed"
             :left 10
             :bottom 30}}
    "xpos: " [:span#xpos]]
   [:pre
    {:style {:width 200
             :position "fixed"
             :left 10
             :bottom 20}}
    "ypos: " [:span#ypos]]])

(defn xy-sfx
  [xpos ypos]
  (oset! (js/document.getElementById "xpos") "textContent" xpos)
  (oset! (js/document.getElementById "ypos") "textContent" ypos))

(defn pntr-sfx
  [e m]
  (oset! (js/document.getElementById "xpnt") "textContent"
   (oget e "detail.contact.currentPointerEvent.clientX"))
  (oset! (js/document.getElementById "ypnt") "textContent"
   (oget e "detail.contact.currentPointerEvent.clientY"))
  (oset! (js/document.getElementById "dltx") "textContent"
   (oget e "detail.global.deltaX"))
  (oset! (js/document.getElementById "dlty") "textContent"
   (oget e "detail.global.deltaY"))
  (oset! (js/document.getElementById "actv") "textContent" m))
