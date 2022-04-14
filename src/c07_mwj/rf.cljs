(ns c07-mwj.rf
  (:require
   [re-frame.core :as re-frame]))

(def default-db
  {})

(def >evt re-frame/dispatch)
(def <sub (comp deref re-frame/subscribe))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   default-db))

; (re-frame/reg-sub
;  ::name
;  (fn [db]
;    (:name db)))
