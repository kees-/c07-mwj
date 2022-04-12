(ns c07-mwj.charms
  (:require
   [c07-mwj.logic :as logic]
   [reagent.core :as reagent]
   [oops.core :as oops
    :refer [oget oset!]]
   ["contactjs" :as contact]))

(defn add-props
  "Add an empty parameter map to hiccup component if not present."
  [hiccup]
  (if (map? (hiccup 1))
    hiccup
    (reduce into [[(hiccup 0)] [{}] (rest hiccup)])))

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
         ; Hacky `this` ?
         me (js/document.getElementById id)
         ; A set of options for the element's gesture listener
         ; https://biodiv.github.io/contactjs/documentation/contact-js/#Options
         opts #js{:DEBUG false}
         ; Add X and Y to a given element's current LEFT and TOP properties
         position (fn [el x y]
                    (oset! el "style.left"
                      (-> el (oget "style.left") nan-zero (+ x) (str "px")))
                    (oset! el "style.top"
                      (-> el (oget "style.top") nan-zero (+ y) (str "px"))))
         ; Directly set the X and Y value of an element's transform property
         translate (fn [el x y]
                     (oset! el "style.transform"
                       (str "translate(" x "px, " y "px)")))]
        ; Spawn the charm at a random point
        ; The element width is used to calculate not spawning near the edge
        ; Upcoming, a coord option to override random spawning
        (oset! me "style.left"
          (-> me (oget "offsetWidth") logic/spawn-x (str "px")))
        (oset! me "style.top"
          (-> me (oget "offsetHeight") logic/spawn-y (str "px")))
        ; Reveal the charm in the rendered DOM
        (-> me (oget "classList") (.remove "hidden"))
        ; Create the all-important all-hearing Contact.js listener
        (contact/PointerListener. me opts)
        ; Function which fires WHILE DRAGGING a charm
        (.addEventListener me "pan"
         (fn [e]
           (translate me
             (oget e "detail.global.deltaX")
             (oget e "detail.global.deltaY"))))
        ; Function which fires when user RELEASES charm
        ; Ending a pan 'locks in' the (x,y) offset of the gesture
        ; The next pan will begin with a delta of (0, 0), so set that now
        (.addEventListener me "panend"
         (fn [e]
           ; Update the element's base position
           (position me
             (oget e "detail.global.deltaX")
             (oget e "detail.global.deltaY"))
           ; Revert the transform to original position
           (translate me 0 0)))))}))
