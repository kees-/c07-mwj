(ns c07-mwj.logic)

(defn year
  []
  (.getFullYear (js/Date.)))

(defn spawn-x
  [v]
  (+ v (rand (- (.. js/window -innerWidth) (* 3 v)))))

(defn spawn-y
  [v]
  (+ v (rand (- (.. js/window -innerHeight) (* 3 v)))))
