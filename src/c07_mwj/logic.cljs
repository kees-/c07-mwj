(ns c07-mwj.logic
  (:require
   [oops.core :as oops
    :refer [oget]]))

(defn year
  "Current year"
  []
  (.getFullYear (js/Date.)))

(defn ww
  []
  (oget js/window "innerWidth"))

(defn wh
  []
  (oget js/window "innerHeight"))

(defn spawn-x
  "Find a suitable random spawn point pixel value"
  [v]
  (+ v (rand (- (ww) (* 2.5 v)))))

(defn spawn-y
  [v]
  "Find a suitable random spawn point pixel value"
  (+ v (rand (- (wh) (* 2.5 v)))))
