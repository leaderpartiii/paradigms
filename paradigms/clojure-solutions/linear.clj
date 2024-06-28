(defn op [f]
  (fn [& arguments]
    (apply mapv f arguments)
    )
  )

(def v+ (op +))
(def v* (op *))
(def vd (op /))
(def v- (op -))

(def m+ (op v+))
(def m* (op v*))
(def md (op vd))
(def m- (op v-))

(def c+ (op m+))
(def c* (op m*))
(def cd (op md))
(def c- (op m-))

(def c4+ (op c+))
(def c4* (op c*))
(def c4d (op cd))
(def c4- (op c-))

(defn scalar [vector1 vector2]
  (apply + (v* vector1 vector2))
  )
(defn vOneScalar [func vect scalar]
  (mapv (fn [x] (func x scalar)) vect)
  )
(defn v*s [vect scalar]
  (vOneScalar * vect scalar)
  )

(defn m*s [matrix scalar]
  (vOneScalar v*s matrix scalar)
  )
(defn m*v [matrix vector1]
  (vOneScalar scalar matrix vector1)
  )
(defn det2x2 [vect1 vect2]
  (- (* (nth vect1 0)
        (nth vect2 1))
     (* (nth vect1 1)
        (nth vect2 0)))
  )
(defn vect [vector1 vector2]
  (vector (det2x2 [(nth vector1 1) (nth vector1 2)]
                  [(nth vector2 1) (nth vector2 2)])
          (- (det2x2 [(nth vector1 0) (nth vector1 2)]
                     [(nth vector2 0) (nth vector2 2)]))
          (det2x2 [(nth vector1 0) (nth vector1 1)]
                  [(nth vector2 0) (nth vector2 1)]))
  )
(defn transpose [matrix]
  (apply mapv vector matrix)
  )
(defn m*m [matrix1 matrix2]
  (transpose (mapv (partial m*v matrix1) (transpose matrix2)))
  )
