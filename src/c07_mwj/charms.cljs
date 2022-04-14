(ns c07-mwj.charms
  (:require
   [c07-mwj.logic :as logic]
   [c07-mwj.debug :as debug]
   [reagent.core :as reagent]
   [oops.core :as oops
    :refer [oget oget+ oset!]]
   ["contactjs" :as contact]))

(defn add-props
  "Add an empty parameter map to hiccup component if not present."
  [c]
  (if (map? (c 1))
    c
    (reduce into [[(c 0)] [{}] (rest c)])))

(defn boundary-collisions
  "Returns whether an element is colliding with outer walls, and which ones"
  [element]
  (let [; 2-step parse js obj to symbols
        corners (fn [o] (map #(oget+ o %) ["top" "bottom" "left" "right"]))
        [t b l r] (-> element .getBoundingClientRect corners)
        collisions (-> {}
                       (assoc :t? (< t 0))
                       (assoc :b? (< (logic/wh) b))
                       (assoc :l? (< l 0))
                       (assoc :r? (< (logic/ww) r)))]
    ; Map of booleans for each side the element is touching
    collisions))

;; NOTES
;  Set the ID for the charm in the options map (:id), not in the component.
;
;  The mindset here is that you can theoretically pass any component to the
;  charmer. As long as its children are compatible with the movement gestures,
;  most HTML should be safe. The status of form components are unknown.
(defn charm
  "Return a generic charm element. Pass a map of params and a hiccup form."
  [{:keys [id] :or {}} content]
  (reagent/create-class
   {:reagent-render
    (fn []
      (-> content
          ; Add property map if missing
          add-props
          ; Set the ID of the component
          (assoc-in [1 :id] id)
          ; Append necessary classes for charm display
          (update-in [1 :class] str " charm no-select hidden")))
    :component-did-mount
    (fn []
      (let
        [; Avoid NaN error preventing addition. Empty string equivalent to 0
         nan-zero (fn [v] (if (= "" v) 0 (js/parseFloat v)))
         ; Avoid eye rolling errors when setting CSS values
         px (fn [v] (str v "px"))
         ; Hacky `this`
         me (js/document.getElementById id)
         ; A set of options for the element's gesture listener
         ; https://biodiv.github.io/contactjs/documentation/contact-js/#Options
         opts #js{:DEBUG false}
         ; Directly set the X and Y value of an element's transform property
         translate (fn [el x y]
                     (oset! el "style.transform"
                       (str "translate(" x "px, " y "px)")))
         left (fn [el v] (oset! el "style.left" (str v "px")))
         top (fn [el v] (oset! el "style.top" (str v "px")))]
        ; Spawn the charm at a random point
        ; The element width is used to calculate not spawning near the edge
        ; Upcoming, a coord option to override random spawning
        (left me (-> me (oget "offsetWidth") logic/spawn-x))
        (top me (-> me (oget "offsetHeight") logic/spawn-y))
        ; Reveal the charm in the rendered DOM
        (-> me (oget "classList") (.remove "hidden"))
        ; Create the all-important all-hearing Contact.js listener
        (contact/PointerListener. me opts)
        ; Function which fires WHILE DRAGGING a charm
        (.addEventListener me "pan"
         (fn [e]
           (let [; Refer above
                 {:keys [t? b? l? r?]} (boundary-collisions me)
                 ; A string "up" "right" etc for most recent gesture direction
                 d (oget e "detail.live.direction")
                 ; Shorthand event access
                 x (oget e "detail.global.deltaX")
                 y (oget e "detail.global.deltaY")
                 ; Two lines to get paramters of element AS IT IS
                 bcr (.getBoundingClientRect me)
                 [w h l t] (map #(oget+ bcr %) ["width" "height" "left" "top"])
                 ; For getting CSS properties
                 x-origin (nan-zero (oget me "style.left"))
                 y-origin (nan-zero (oget me "style.top"))]
             ; The element will be repositioned no matter if it is colliding.
             ; Each branch corresponds to a side.
             ; Logic responds to gestures that move AWAY from the current side.
             ; If movement is occur at all upon collision,
             ; the element sticks to the wall.
             ; In such cases the easiest thing to do is adjust another factor.
             ; Setting .-left and .-top allow the element to compensate when
             ; it would otherwise be dragged off the page.
             ; Performance could be worse and the visual is really not bad.
             ; The +1/-1 values for d != -_? seem to fix strange location bugs.
             (translate me
              (cond
                l? (if (= d "right")
                     (do (left me (- x l))
                       x)
                     (- (* -1 x-origin) 1))
                r? (if (= d "left")
                     (do (left me (- (logic/ww) w x))
                       x)
                     (- (logic/ww) x-origin (oget me "offsetWidth") -1))
                :else x)
              (cond
                t? (if (= d "down")
                     (do (top me (- y t))
                       y)
                     (- (* -1 y-origin) 1))
                b? (if (= d "up")
                     (do (top me (- (logic/wh) h y))
                       y)
                     (- (logic/wh) y-origin (oget me "offsetHeight") -1))
                :else y))
             ; DEBUG
             #_(debug/xy-sfx l t)
             #_(debug/pntr-sfx e "pan"))))
        ; Function which fires when user RELEASES charm
        ; Ending a pan 'locks in' the (x,y) offset of the gesture
        ; The next pan will begin with a delta of (0, 0), so set that now
        (.addEventListener me "panend"
         (fn [e]
           (let [x (-> me .getBoundingClientRect (oget "x"))
                 y (-> me .getBoundingClientRect (oget "y"))]
             ; Update the element's base position
             (left me (if (< x 0) -1 x))
             (top me (if (< y 0) -1 y))
             ; Revert the transform to original position
             (translate me 0 0)
             ; DEBUG
             #_(debug/xy-sfx x y)
             #_(debug/pntr-sfx e "panend"))))))}))
