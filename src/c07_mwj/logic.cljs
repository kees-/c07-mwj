(ns c07-mwj.logic)

(defn year
  "Current year"
  []
  (.getFullYear (js/Date.)))

(defn spawn-x
  "Find a suitable random spawn point pixel value"
  [v]
  (+ v (rand (- (.. js/window -innerWidth) (* 2.5 v)))))

(defn spawn-y
  [v]
  "Find a suitable random spawn point pixel value"
  (+ v (rand (- (.. js/window -innerHeight) (* 2.5 v)))))
