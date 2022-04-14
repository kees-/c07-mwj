(ns c07-mwj.re-frame
  (:require
   [re-frame.core :as rf]))

(def default-db
  {})

(def >evt rf/dispatch)
(def <sub (comp deref rf/subscribe))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

; (re-frame/reg-sub
;  ::name
;  (fn [db]
;    (:name db)))
