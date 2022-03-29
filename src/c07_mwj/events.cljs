(ns c07-mwj.events
  (:require
   [re-frame.core :as re-frame]
   [c07-mwj.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
