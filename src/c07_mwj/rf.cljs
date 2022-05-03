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
