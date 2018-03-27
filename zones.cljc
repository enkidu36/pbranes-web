(ns pbranes-web.zones)

;; Zone calc methods
(def coggin
  [["Active Recovery" 0.00 0.55]
   ["Enduranceeeeee" 0.56 0.75]
   ["Tempo" 0.76 0.90]
   ["Lactate Threshold" 0.90 1.05]
   ["VO2 Max" 1.06 1.20]
   ["Anaerobic Capacity" 1.21 1.50]])


(defn calc-pct [ftp-value pct]
  (if (zero? (int ftp-value))
    ""
    (int (* pct ftp-value))))

(defn range-text [lower upper]
  (str (int (* 100 lower)) " - " (int (* 100 upper))))


(defn calc-zone [row ftp-value]
  (let [[name lower upper :as zone] row]
    [name (calc-pct ftp-value lower) (calc-pct ftp-value upper) (range-text lower upper)]))

(defn calc-power [method ftp-value]
  (vec (map #(conj (calc-zone % ftp-value)) method)))


