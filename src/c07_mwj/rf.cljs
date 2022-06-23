(ns c07-mwj.rf
  (:require
   [re-frame.core :as re-frame
    :refer [reg-event-db reg-event-fx reg-sub]]))

;; ========== INITIAL SETUP ====================================================
(def default-db
  {})

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
   (js/console.info "Acknowledging temporary event")))

;; ========== SUBS =============================================================
(reg-sub
 ::full-list
 (fn [db]
   (:charm-list db)))

(reg-sub
 ::get-charm
 :<- [::full-list]
 (fn [list [_ id]]
   (->> list
        (filter #(= id (:id %)))
        first)))
