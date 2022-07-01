(ns c07-mwj.rf
  (:require
   [re-frame.core :as re-frame
    :refer [reg-event-db reg-event-fx reg-sub reg-fx]]))

;; ========== INITIAL SETUP ====================================================
(def default-db
  {:charm-list nil
   :current-charms #{}})

(def >evt re-frame/dispatch)
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
   (let [init-charms (<sub [::get-charms-by-parent "zap"])
         ids (map #(:id %) init-charms)]
     (>evt [::add-charms ids])
     (js/console.info "Acknowledging temporary event"))))

(reg-event-db
 ::add-charms
 (fn [db [_ ids]]
   (update-in db [:current-charms] into ids)))

;; ========== SUBS =============================================================
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
 (fn [db [_ & msg]]
   (:current-charms db)))
