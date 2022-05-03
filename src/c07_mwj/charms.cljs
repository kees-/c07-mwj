(ns c07-mwj.charms
  (:require
   [c07-mwj.logic :as logic]
   [c07-mwj.debug :as debug]
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [oops.core :as oops
    :refer [oget oget+ oset!]]))

(defn add-props
  "Add an empty parameter map to hiccup component if not present."
  [c]
  (if (map? (c 1))
    c
    (reduce into [[(c 0)] [{}] (rest c)])))

(defn charm
  "Return a generic charm element. Pass a map of params and a hiccup form."
  [{:keys [id container contains] :or {contains []}}]
  (reagent/create-class
   {:display-name id
    :reagent-render
    (fn []
      (-> container
          ; Add property map if missing
          add-props
          ; Set the ID of the component
          (assoc-in [1 :id] id)
          ; Append necessary classes for charm display
          (update-in [1 :class] str " charm no-select")))
    :component-did-mount
    (fn [me]
      (let [this (rdom/dom-node me)]
        ; Spawn the charm at a random point
        ; The element width is used to calculate not spawning near the edge
        ; Upcoming, a coord option to override random spawning
        (oset! this "style.left"
         (-> this (oget "offsetWidth") logic/spawn-x (str "px")))
        (oset! this "style.top"
         (-> this (oget "offsetHeight") logic/spawn-y (str "px")))))}))
