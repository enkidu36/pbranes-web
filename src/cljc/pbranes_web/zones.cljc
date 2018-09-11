(ns pbranes-web.zones)

;; Zone calc methods
;; TODO: put these in database??
(def coggin
  {:method-name "Coggin"
   :zones       [["Active Recovery" 0.00 0.55, 0.00 0.69]
                 ["Endurance" 0.55 0.75, 0.69 0.84]
                 ["Tempo" 0.75 0.90, 0.84 0.95]
                 ["Lactate Threshold" 0.90 1.05, 0.95 1.05]
                 ["VO2 Max" 1.05 1.20, 1.06 0.00]
                 ["Anaerobic Capacity" 1.20 0.00]]})

(def friel
  {:method-name "Friel"
   :zones       [["1 - Active Recovery" 0.00 0.55, 0.00 0.81]
                 ["2 - Endurance" 0.55 0.75, 0.81 0.90]
                 ["3 - Tempo" 0.75 0.90, 0.90 0.94]
                 ["4 - Lactate Threshold" 0.90 1.05, 0.94 1.00]
                 ["5a - VO2 Max" 1.05 1.20, 1.00 1.03]
                 ["5b - Anaerobic Capacity" 1.20 1.50, 1.03 1.06]
                 ["5c - Neuro muscular" 1.50 0.00, 1.06 0.00]]})

(def joe
  {:method-name "Joe's"
   :zones       [["1 - Easy day" 0.00 0.55, 0.00 0.81]
                 ["2 - LSD" 0.55 0.75, 0.81 0.90]
                 ["3 - Comfortably hard" 0.75 0.90, 0.90 0.94]
                 ["4 - Yeah baby" 0.90 1.05, 0.94 1.00]
                 ["5a - Not good Mav" 1.05 1.20, 1.00 1.03]
                 ["5b - A very dark place" 1.20 1.50, 1.03 1.06]
                 ["5c - Pain is temporary" 1.50 0.00, 1.06 0.00]
                 ["" 0.00 0.00, 0.00 0.00]
                 ["Sweet Spot" 0.975 1.125, 0.97 1.01]]})

