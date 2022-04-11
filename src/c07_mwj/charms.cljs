(ns c07-mwj.charms
  (:require
   [c07-mwj.logic :as logic]
   [reagent.core :as reagent]
   ["contactjs" :as contact]))

(defn add-props
  [hiccup]
  (if (map? (hiccup 1))
    hiccup
    (reduce into [[(hiccup 0)] [{}] (rest hiccup)])))

(defn charm
  [{:keys [id]
    :or {}}
   content]
  (reagent/create-class
   {:reagent-render
    (fn []
      (-> content
          add-props
          (assoc-in [1 :id] id)
          (update-in [1 :class] str " charm no-select hidden")))
    :component-did-mount
    (fn []
      (let [zero (fn [v] (if (= "" v) 0 (js/parseFloat v)))
            el (js/document.getElementById id)
            opts (clj->js {:DEBUG false})
            position (fn [element x2 y2]
                       (set! (.. element -style -left)
                         (-> (.. element -style -left) zero (+ x2) (str "px")))
                       (set! (.. element -style -top)
                         (-> (.. element -style -top) zero (+ y2) (str "px"))))
            translate (fn [element x y]
                        (set! (.. element -style -transform)
                          (str "translate(" x "px, " y "px)")))]
        (set! (.. el -style -left)
          (-> el .-offsetWidth logic/spawn-x (str "px")))
        (set! (.. el -style -top)
          (-> el .-offsetHeight logic/spawn-y (str "px")))
        (-> el .-classList (.remove "hidden"))
        (contact/PointerListener. el opts)
        (.addEventListener el "pan"
         (fn [e]
           (translate el
                 (.. e -detail -global -deltaX)
                 (.. e -detail -global -deltaY))))
        (.addEventListener el "panend"
         (fn [e]
           (translate el 0 0)
           (position el
                     (.. e -detail -global -deltaX)
                     (.. e -detail -global -deltaY))))))}))
