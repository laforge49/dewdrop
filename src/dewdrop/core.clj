(ns dewdrop.core)

(defrecord lens [getter setter])

(defn lget [^lens l data]
  ((.getter l) data))

(defn lset [^lens l data value]
  ((.setter l) data value))

(defn lupdate [^lens l data f]
  (let [old (lget l data)]
    (lset l data (f old))))

(defn ladd [^lens a ^lens b]
  (lens.
    (fn [d] ((.getter b) ((.getter a) d)))
    (fn [d v]
      (let [ad ((.getter a) d)
            nad ((.setter b) ad v)]
        ((.setter a) d nad)))))

(defn key-lens [k]
  (lens.
    (fn [d] (get d k))
    (fn [d v] (assoc d k v))))