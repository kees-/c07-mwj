(ns c07-mwj.utilities
  (:require
   [clojure.string :as str]
   [ajax.core :as ajax]
   [ajax.edn :as edn]
   [reagent.core :as reagent]
   [goog.net.jsloader :as jsl]
   [goog.html.legacyconversions :as legacy]
   [c07-mwj.rf :as rf :refer [>evt <sub]]))

;; ========== DATA REQUESTS ====================================================
(defn load!
  [source]
  (ajax/GET source
    {:response-format (edn/edn-response-format)
     :handler #(>evt [::rf/load-charm-list %])
     :error-handler #(println "The request failed. Response:" %)
     :finally #(>evt [::rf/temporary])}))

;; ========== SCRIPT LOADING ===================================================
(defn filter-loaded
  "Take a map of boolean functions to URI locations (local/remote)
   of javascript script files. Returns a list of URIs
   corresponding to false checks."
  [scripts]
  (reduce (fn [acc [loaded? src]]
            (if (loaded?) acc (conj acc src)))
          []
          scripts))

(defn js-loader
  "Takes a map containing a map of scripts and two DOM components.
   The :loading component is displayed until all scripts have been run.
   The :loaded component will render once setup is complete."
  [{:keys [scripts loading loaded]}]
  (let [loaded? (reagent/atom false)]
    (reagent/create-class
     {:component-did-mount
      (fn []
        (let [not-loaded (->> scripts
                              filter-loaded
                              (map legacy/trustedResourceUrlFromString)
                              clj->js)]
          (.then (jsl/safeLoadMany not-loaded)
                 #(reset! loaded? true)
                 #(js/console.info "Scripts not loaded."))))
      :reagent-render
      (fn []
        (if @loaded? loaded loading))})))

;; ========== FLODESK LOGIC ====================================================
(defn timestamp
  "Flodesk needs to call scripts with a custom timestamp."
  []
  (-> (.now js/Date)
      (/ 120000)
      (js/Math.floor)
      (* 60)))

(defn script-uri
  "Returns a string URI pointing to Flodesk script CDN.
   Provide \".js\" or \".mjs\" for the :file keyword"
  [{:keys [file]}]
  (let [base "https://assets.flodesk.com/universal"
        v (timestamp)]
    (str/join [base file "?v=" v])))

(def externals
  "The specific local and remote script locations
   required for Flodesk components to load."
  {#(exists? (.-fd js/window)) "/js/preFlodesk.js"
   #(nil? 0) (script-uri {:file ".mjs"})
   #(nil? 1) (script-uri {:file ".js"})})

;; ========== COMPONENTS =======================================================
(defn loading
  "Arbitrary loading placeholder."
  []
  [:article
   {:style {:color "red"
            :font-size "3rem"}}
   "Loading, or failed to load"])

(defn contact-capture
  "Returns an identifying :div and renders internal Flodesk logic."
  [{:keys [id]}]
  (reagent/create-class
   {:display-name id
    :component-did-mount
    (fn [_]
      (.fd js/window
           "form"
           (clj->js {:formId id
                     :containerEl (str "#fd-form-" id)})))
    :reagent-render
    (fn [{:keys [id]}]
      [:div {:style {:display "block"
                     :border "1px solid orange"}
             :id (str "fd-form-" id)}])}))

;; ========== SCRIPT LOGIC =====================================================
(defn form-loader
  "Component for just-in-time script loading.
   Replaces the need for header scripts called asynchronously on page load."
  [id]
  [js-loader
   {:scripts externals
    :loading [loading]
    :loaded  [contact-capture {:id id}]}])
