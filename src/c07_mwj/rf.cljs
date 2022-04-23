(ns c07-mwj.rf
  (:require
   [re-frame.core :as re-frame
    :refer [reg-event-db reg-event-fx reg-sub]]))

;; ========== INITIAL SETUP ====================================================
(def default-db
  {:nav
   {:charm-depth 0}})

(def >evt re-frame/dispatch)
(def <sub (comp deref re-frame/subscribe))

;; ========== EVENTS ===========================================================
(reg-event-db
 ::initialize-db
 (fn [_ _]
   default-db))

(reg-event-db
 ::inc-depth
 (fn [db _]
   (update-in db [:nav :charm-depth] inc)))

(reg-event-db
 ::dec-depth
 (fn [db _]
   (update-in db [:nav :charm-depth] dec)))

;; ========== SUBS =============================================================
(reg-sub ::depth #(-> % :nav :charm-depth))
