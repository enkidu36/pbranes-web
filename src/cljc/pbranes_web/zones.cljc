(ns pbranes-web.zones)

;; Zone calc methods
;; TODO: put these in database??
(def coggin
  {:method-name "Coggin"
   :zones       [["Active Recovery" 0.00 0.55]
                 ["Endurance" 0.56 0.75]
                 ["Tempo" 0.76 0.90]
                 ["Lactate Threshold" 0.90 1.05]
                 ["VO2 Max" 1.06 1.20]
                 ["Anaerobic Capacity" 1.21 0.00]]})

(def friel
  {:method-name "Friel"
   :zones       [["1 -  Active Recovery" 0.00 0.55]
                 ["2 -  Endurance" 0.56 0.75]
                 ["3 -  Tempo" 0.76 0.90]
                 ["4 -  Lactate Threshold" 0.90 1.05]
                 ["5a - VO2 Max" 1.06 1.20]
                 ["5b - Anaerobic Capacity" 1.21 1.50]
                 ["5c - Neuro muscular" 1.51 0.00]]})

(defn calc-pct
  ([ftp-value pct]
   (calc-pct ftp-value pct ""))

  ([ftp-value pct default]
  (if-not (zero? (int ftp-value))
    (if-not (zero? pct)
      (int (* pct ftp-value))
      default)
    "")))

(defn range-text
  ([min max]
   (range-text min max "< " " >"))

  ([min max unbounded-lower unbounded-upper]
    (cond
      (and (zero? min) (zero? max)) "no values"
      (zero? min) (str unbounded-lower (int (* 100 max)) "%")
      (zero? max) (str (int (* 100 min)) "%" unbounded-upper)
      :else (str (int (* 100 min)) "%" " - " (int (* 100 max)) "%"))))

(defn calc-zone [zone ftp-value]
  (let [[name lower upper] zone]
    [name
     (calc-pct ftp-value lower "< ")
     (calc-pct ftp-value upper " >")
     (range-text lower upper)]))

(defn calc-power [method ftp-value]
  (vec (map #(conj (calc-zone % ftp-value)) method)))


