(ns pbranes-web.zones)

;; Zone calc methods
;; TODO: put these in database??
(def coggin
  {:method-name "Coggin"
   :zones       [["Active Recovery" 0.00 0.55]
                 ["Endurance" 0.55 0.75]
                 ["Tempo" 0.75 0.90]
                 ["Lactate Threshold" 0.90 1.05]
                 ["VO2 Max" 1.05 1.20]
                 ["Anaerobic Capacity" 1.20 0.00]]})

(def friel
  {:method-name "Friel"
   :zones       [["1 - Active Recovery" 0.00 0.55]
                 ["2 - Endurance" 0.55 0.75]
                 ["3 - Tempo" 0.75 0.90]
                 ["4 - Lactate Threshold" 0.90 1.05]
                 ["5a - VO2 Max" 1.05 1.20]
                 ["5b - Anaerobic Capacity" 1.20 1.50]
                 ["5c - Neuro muscularrrrrr" 1.50 0.00]]})

(def joe
  {:method-name "Joe's"
   :zones       [["1 - Easy day" 0.00 0.55]
                 ["2 - LSD" 0.55 0.75]
                 ["3 - Comfortably hard" 0.75 0.90]
                 ["4 - Yeah baby" 0.90 1.05]
                 ["5a - Not good Mav" 1.05 1.20]
                 ["5b - A very dark place" 1.20 1.50]
                 ["5c - Pain is temporay" 1.50 0.00]]})


(def LOWER-BOUND-SYMBOL " > ")
(def UPPER-BOUND-SYMBOL " < ")

(defn calc-pct
  ([ftp-value pct]
   (calc-pct ftp-value pct ""))

  ([ftp-value pct default]
  (if-not (zero? (int ftp-value))
    (if-not (zero? pct)
      (* pct ftp-value)
  default)
    "")))

(defn floor [value]
  (if (number? value)
    (Math/floor value)
    value))

(defn ceil [value]
  (if (number? value)
    (Math/ceil value)
    value))

(defn range-text
  ([min max]
   (range-text min max LOWER-BOUND-SYMBOL UPPER-BOUND-SYMBOL))

  ([min max lower-symbol upper-symbol]
    (cond
      (and (zero? min) (zero? max)) "no values"
      (zero? min) (str lower-symbol (int (floor (* 100 max))) "%")
      (zero? max) (str (int (ceil (* 100 min))) "%" upper-symbol)
      :else (str (int (ceil (* 100 min))) "%" " - " (int (floor (* 100 max))) "%"))))

(defn adj-pct [pct]
  (if-not (zero? pct)
    (+ 0.0005 pct)
    pct))

(defn calc-zone [zone ftp-value]
  (let [[name min max] zone
        lower (adj-pct min)
        upper (adj-pct max)]
    [name
     (ceil (calc-pct ftp-value lower LOWER-BOUND-SYMBOL))
     (floor (calc-pct ftp-value upper UPPER-BOUND-SYMBOL))
     (range-text lower upper)]))

(defn calc-power [method ftp-value]
  (vec (map #(conj (calc-zone % ftp-value)) method)))


