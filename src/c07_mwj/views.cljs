(ns c07-mwj.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [c07-mwj.subs :as subs]
   ["contactjs" :as contact]))

(defn spawn-x
  [v]
  (+ v (rand (- (.. js/window -innerWidth) (* 3 v)))))

(defn spawn-y
  [v]
  (+ v (rand (- (.. js/window -innerHeight) (* 3 v)))))

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

(defn charm
  [{:keys [id glyph title]
    :or {title "Hello!"}}]
  (reagent/create-class
   {:reagent-render
    (fn []
      [:div.charm.no-select.hidden
       {:id id
        :style {:top 0 :left 0}}
       [:span title]
       [:article (str (char glyph))]])
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
        (set! (.. el -style -left) (-> el .-offsetWidth spawn-x (str "px")))
        (set! (.. el -style -top) (-> el .-offsetHeight spawn-y (str "px")))
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

(defn main-panel []
  (let []
    [:div
     [logo]
     [charm {:id "citrus-cage" :glyph 4305 :title "Now in bloom"}]
     [footer]]))
