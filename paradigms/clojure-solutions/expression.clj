(def constant constantly)
(defn variable [vars]
  (fn [args] (args vars)))

(defn abstractOp [op]
  (fn [& args]
    (fn [env]
      (apply op (mapv #(double (% env)) args))
      )))
(def negate (abstractOp -))
(def add (abstractOp +))
(def subtract (abstractOp -))
(def multiply (abstractOp *))
(def sinh (abstractOp #(Math/sinh %)))
(def cosh (abstractOp #(Math/cosh %)))
(defn divide [a b]
  (fn [env] (/ (double (a env)) (double (b env)))))
(def ops {
          '-      subtract,
          '+      add,
          '/      divide,
          '*      multiply,
          'negate negate,
          'sinh   sinh,
          'cosh   cosh
          })
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn expression [evaluate toString]
  {:evaluate evaluate
   :toString toString})
(defn toString [expression]
  ((:toString expression)))
(defn evaluate [expression vars]
  ((:evaluate expression) vars))
(defn Constant [a]
  (expression
    (fn [_] (double a))
    (fn [] (str a))
    ))
(defn Variable [a]
  (expression
    (fn [vars] (get vars a))
    (fn [] (str a))
    ))
(defn AbstractOperation [f toStr]
  (fn [& args]
    (expression
      (fn [vars]
        (apply f (mapv #(evaluate % vars) args)))
      (fn []
        (str "("
             toStr " "
             (clojure.string/join " " (mapv #(str (toString %)) args))
             ")"))
      )))
(def Add (AbstractOperation + "+"))
(def Subtract (AbstractOperation - "-"))
(def Multiply (AbstractOperation * "*"))
(defn Divide [a b]
  (expression
    (fn [vars] (/ (double (evaluate a vars)) (double (evaluate b vars))))
    (fn [] (str "(" "/ " (toString a) " " (toString b) ")"))))
(def Negate (AbstractOperation - "negate"))
(def Sinh (AbstractOperation #(Math/sinh %) "sinh"))
(def Cosh (AbstractOperation #(Math/cosh %) "cosh"))
(def Exp (AbstractOperation #(Math/exp %) "exp"))
(def Ln (AbstractOperation #(Math/log %) "ln"))
(def Pow (AbstractOperation #(Math/pow %1 %2) "pow"))
(def Log (AbstractOperation #(/ (Math/log (abs %2)) (Math/log (abs %1))) "log"))
(def Ops {
          '-      Subtract,
          '+      Add,
          '/      Divide,
          '*      Multiply,
          'negate Negate,
          'sinh   Sinh,
          'cosh   Cosh,
          'exp    Exp,
          'ln     Ln,
          'pow    Pow,
          'log    Log
          })
(defn parsing [obj variables constants mapOperation]
  (fn [args]
    (cond
      (string? args) ((parsing obj variables constants mapOperation) (read-string args))
      (number? args) (constants args)
      (list? args) (apply (mapOperation (first args)) (map (parsing obj variables constants mapOperation) (rest args)))
      (symbol? args) (variables (name args))
      )))
(def parseObject (parsing parseObject Variable Constant Ops))
(def parseFunction (parsing parseFunction variable constant ops))
