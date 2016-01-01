# dewdrop
Simple Clojure Lenses

[![Clojars Project](https://img.shields.io/clojars/v/dewdrop.svg)](https://clojars.org/dewdrop)

A lens lets you operate on part of a structure.

Lets say you have a map and you want to operate on the value of :x.
You would define the lens like this:

```
(def x-lens (key-lens :x))
```
Here are some sample tests:

```
(println (lset x-lens {} 5))
;-> {:x 5}
(println (lget x-lens {:x 5 :y 6}))
;-> 5
(println (lget x-lens {}))
;-> nil
(println (lset x-lens nil 5))
;-> {:x 5}
(println (lupdate x-lens {:x 5 :y 6}
                  (fn [old] (* 2 old))))
;-> {:x 10, :y 6}
```
Now lets create a second lens for operating on the value of :y in a map:

```
(def y-lens (key-lens :y))
```
But what if the value of :y is found in the map which :x holds?
We just add the lenses together:
```

(def xy-lens (ladd x-lens y-lens))
```
And here are some more tests:

```
(println (lset xy-lens nil 5))
;-> {:x {:y 5}
(println (lget xy-lens {:x {:y 5 :z 3}}))
;-> 5
(println (lupdate xy-lens {:x {:y 5 :z 3}}
                  (fn [old] (* 2 old))))
;-> {:x {:y 10, :z 3}}
```

## Write your own lenses

A dewdrop lens is nothing more than a record with getter and setter functions as values:

```
(defrecord lens [getter setter])
```
Defining a kind of lens then is very simple, and you can easily define lenses for
different types of data structures.
Here is the key-lens function we used above for accessing maps:

```
(defn key-lens [k]
  (lens.
    (fn [d] (get d k))
    (fn [d v] (assoc d k v))))
```
Rounding things out then, are the lget, lset, lupdate and ladd functions:

```
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
```
