(ns c07-mwj.charms
  (:require
   [c07-mwj.logic :as logic]
   #_[c07-mwj.debug :as debug]
   [c07-mwj.rf :as rf :refer [<sub >evt >evt-now]]
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [oops.core :as oops :refer [oget oset!]]))

(defn register-charm
  []
  (if (<sub [::rf/listener-set-up?])
    (js/console.log "The listener is already set up")
    (>evt-now [::rf/set-up-listener])))

(defn add-props
  "Add an empty parameter map to hiccup component if not present."
  [c]
  (if (map? (c 1))
    c
    (reduce into [[(c 0)] [{}] (rest c)])))

(defn rf-charm
  "Component for a charm"
  [id]
  (let [{:keys [id exterior interior]} (<sub [::rf/get-charm id])]
    (reagent/create-class
     {:display-name id
      :reagent-render
      (fn []
        (-> exterior
            add-props
            (assoc-in [1 :id] id)
            (update-in [1 :class] str "charm no-select hidden")))
      :component-did-mount
      (fn [me]
        (register-charm)
        (let [this (rdom/dom-node me)]
          (oset! this
                 "style.left"
                 (-> this (oget "offsetWidth") logic/spawn-x (str "px")))
          (oset! this
                 "style.top"
                 (-> this (oget "offsetHeight") logic/spawn-y (str "px")))
          (.remove (.-classList this) "hidden")))})))
