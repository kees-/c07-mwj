(ns c07-mwj.rf
  (:require
   [re-frame.core :as re-frame
    :refer [reg-event-db reg-event-fx reg-sub reg-fx]]
   ["gsap/Draggable$Draggable" :as Draggable]))

;; ========== INITIAL SETUP ====================================================
(def default-db
  {:charm-list nil
   :current-charms #{}
   :listener-set-up false})

(def >evt re-frame/dispatch)
(def >evt-now re-frame/dispatch-sync)
(def <sub (comp deref re-frame/subscribe))

;; ========== EVENTS ===========================================================
(reg-event-db
 ::initialize-db
 (fn [_ _]
   default-db))

(reg-event-db
 ::load-charm-list
 (fn [db [_ data]]
   (assoc-in db [:charm-list] data)))

(reg-event-db
 ::temporary
 (fn [_ _]
   (let [init-charms (<sub [::get-charms-by-parent "root"])
         ids (map #(:id %) init-charms)]
     (>evt [::add-charms ids]))))

(reg-fx
 :set-up-draggable
 (fn [_]
   (Draggable/create
    ".charm"
    #js{:bounds "#canvas"
        :onClick #(js/console.log (char 9786))})))

(reg-event-db
 ::add-charms
 (fn [db [_ ids]]
   (update-in db [:current-charms] into ids)))

(reg-event-fx
 ::set-up-listener
 (fn [cofx _]
   {:db (assoc-in (:db cofx) [:listener-active] true)
    :set-up-draggable nil}))

;; ========== SUBS =============================================================
(reg-sub
 ::listener-set-up?
 (fn [db]
   (:listener-active db)))

(reg-sub
 ::full-list
 (fn [db]
   (:charm-list db)))

(reg-sub
 ::get-charm
 :<- [::full-list]
 (fn [full-list [_ id]]
   (->> full-list
        (filter #(= id (:id %)))
        first)))

(reg-sub
 ::get-charms-by-parent
 :<- [::full-list]
 (fn [full-list [_ id]]
   (->> full-list
        (filter #(= id (:parent %))))))

(reg-sub
 ::current-charms
 (fn [db _]
   (:current-charms db)))
