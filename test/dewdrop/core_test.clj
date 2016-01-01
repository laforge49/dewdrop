(ns dewdrop.core-test
  (:require [clojure.test :refer :all]
            [dewdrop.core :refer :all])
  (:import [dewdrop.core lens]))

(def x-lens (key-lens :x))
(println (lset x-lens {} 5))
(println (lget x-lens {:x 5 :y 6}))
(println (lget x-lens {}))
(println (lset x-lens nil 5))
(println (lupdate x-lens {:x 5 :y 6}
                  (fn [old] (* 2 old))))

(def y-lens (key-lens :y))
(def xy-lens (ladd x-lens y-lens))
(println (lset xy-lens nil 5))
(println (lget xy-lens {:x {:y 5 :z 3}}))
(println (lupdate xy-lens {:x {:y 5 :z 3}}
                  (fn [old] (* 2 old))))
