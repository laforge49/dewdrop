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
Now lets create a second lens for operating on the value of :y in a map:

```
(def y-lens (key-lens :y))
```
But what if the value of :y is found in the map which :x holds?
We just add the lenses together:
```

(def xy-lens (ladd x-lens y-lens))
```
And here is the test code:

```
(println (lset xy-lens nil 5))
(println (lget xy-lens {:x {:y 5 :z 3}}))
(println (lupdate xy-lens {:x {:y 5 :z 3}}
                  (fn [old] (* 2 old))))
```
