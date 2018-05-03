(ns pbranes-web.calculator.power)


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

(defn calc-power [zone ftp-value]
  (let [[name min max] zone
        lower (adj-pct min)
        upper (adj-pct max)]
    [name
     (ceil (calc-pct ftp-value lower LOWER-BOUND-SYMBOL))
     (floor (calc-pct ftp-value upper UPPER-BOUND-SYMBOL))
     (range-text lower upper)]))

(defn calc-heartrate [zone heartrate-value]
  (let [[name _ _ min max] zone
        lower (adj-pct min)
        upper (adj-pct max)]
    (if (nil? min)
      [name "" "" "Maximal"]
      [name
       (ceil (calc-pct heartrate-value lower LOWER-BOUND-SYMBOL))
       (floor (calc-pct heartrate-value upper UPPER-BOUND-SYMBOL))
       (range-text lower upper)])))

(defn calculate [method value type]
  (if (= "heartrate" type)
    (vec (map #(conj (calc-heartrate % value)) method))
    (vec (map #(conj (calc-power % value)) method))))


