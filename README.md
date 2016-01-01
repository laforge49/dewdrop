# dewdrop
Simple Clojure Lenses

A lens lets you operate on part of a structure.

Lets say you have a map and you want to operate on the value of :x.
You would define the lens like this:

```
(def x-lens (key-lens :x))
```
Here is some sample tests:

```
(println (lset x-lens {} 5))
(println (lget x-lens {:x 5 :y 6}))
(println (lget x-lens {}))
(println (lset x-lens nil 5))
(println (lupdate x-lens {:x 5 :y 6}
                  (fn [old] (* 2 old))))
```