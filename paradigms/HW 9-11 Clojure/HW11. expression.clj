(defn constant [val] (fn [x] val))
(defn variable [name] (fn [x] (x name)))
(defn operation [f] (fn [& operands] (fn [args] (apply f (map (fn [x] (x args)) operands)))))

(def add (operation +))
(def subtract (operation -))
(def negate subtract)
(def multiply (operation *))
(def divide (operation (fn [a b] (/ (double a) (double b)))))
(def min (operation (fn [& x] (reduce (fn [a b] (Math/min a b)) (first x) (rest x)))))
(def max (operation (fn [& x] (reduce (fn [a b] (Math/max a b)) (first x) (rest x)))))

(def operations {'+ add '- subtract '* multiply '/ divide 'negate negate 'min min 'max max})

(defn parseFunction [expr]
    (cond
        (string? expr) (parseFunction (read-string expr))
        (number? expr) (constant expr)
        (symbol? expr) (variable (str expr))
        (seq? expr) (apply (get operations (first expr)) (map parseFunction (rest expr)))))

(defn ln [x] (Math/log (Math/abs x)))
(defn pow [x y] (Math/pow x y))

(defn proto-get [obj key]
    (cond
        (contains? obj key) (obj key)
        (contains? obj :prototype) (proto-get (obj :prototype) key)
        :else nil))
(defn proto-call [this key & args]
    (apply (proto-get this key) this args))
(defn field [key]
    (fn [this] (proto-get this key)))
(defn method [key]
    (fn [this & args] (apply proto-call this key args)))
(defn constructor [cons prototype]
    (fn [& args] (apply cons {:prototype prototype} args)))



(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))

(def emptyPrototype {})
(defn ConstantCons [this val] (let [value (field :value)]
    {
        :value val
        :toString (fn [this] (format "%.1f" (value this)))
        :evaluate (fn [this x] (value this))
        :diff (fn [this x] ((constructor ConstantCons emptyPrototype) 0))}))
(def Constant (constructor ConstantCons emptyPrototype))
(def ZERO (Constant 0))


(defn VariableCons [this variable] (let [sign (field :sign)]
    {
        :sign variable
        :toString (fn [this] (sign this))
        :evaluate (fn [this x] (x (sign this)))
        :diff (fn [this x] (if (= x (sign this)) (Constant 1) ZERO))}))
(def Variable (constructor VariableCons emptyPrototype))

(def _fir (field :fir))
(def _sec  (field :sec))
(def _strRepr (field :strRepr))
(def _action (field :action))

(def UnaryPrototype
    {
        :toString (fn [this] (str "(" (_strRepr this) " " (toString (_fir this)) ")"))
        :evaluate (fn [this x] ((_action this) (evaluate (_fir this) x)))})
(defn UnaryCons [this fir]
    (assoc this :fir fir))

(def BinaryPrototype
    {
        :toString (fn [this] (str "(" (_strRepr this) " " (toString (_fir this)) " " (toString (_sec this)) ")"))
        :evaluate (fn [this x] ((_action this) (evaluate (_fir this) x) (evaluate (_sec this) x)))
    })
(defn BinaryCons [this fir sec]
    (assoc this
        :fir fir
        :sec sec))


(def Negate)
(def NegatePrototype
    (assoc UnaryPrototype :strRepr "negate" :action -
        :diff (fn [this x] (Negate (diff (_fir this) x)))))
(def Negate (constructor UnaryCons NegatePrototype))

(def Add)
(def AddPrototype
    (assoc BinaryPrototype :strRepr "+" :action +
        :diff (fn [this x] (Add (diff (_fir this) x) (diff (_sec this) x)))))
(def Add (constructor BinaryCons AddPrototype))

(def Subtract)
(def SubtractPrototype
    (assoc BinaryPrototype :strRepr "-" :action -
        :diff (fn [this x] (Subtract (diff (_fir this) x) (diff (_sec this) x)))))
(def Subtract (constructor BinaryCons SubtractPrototype))

(def Multiply)
(def MultiplyPrototype
    (assoc BinaryPrototype :strRepr "*" :action *
        :diff (fn [this x] (Add (Multiply (diff (_fir this) x) (_sec this))
                                  (Multiply (_fir this) (diff (_sec this) x))))))
(def Multiply (constructor BinaryCons MultiplyPrototype))

(def Divide)
(def DividePrototype
    (assoc BinaryPrototype :strRepr "/" :action (fn [x y] (/ (double x) (double y)))
        :diff (fn [this x] (Divide
                                    (Subtract
                                        (Multiply (diff (_fir this) x) (_sec this))
                                        (Multiply (_fir this) (diff (_sec this) x)))
                                    (Multiply (_sec this) (_sec this))))))
(def Divide (constructor BinaryCons DividePrototype))

(def Ln)
(def LnPrototype
    (assoc UnaryPrototype :strRepr "ln" :action ln
            :diff (fn [this x] (Divide (diff (_fir this) x) (_fir this)))))
(def Ln (constructor UnaryCons LnPrototype))

(def Pw)
(def PwPrototype
    (assoc BinaryPrototype :strRepr "pw" :action pow
         :diff (fn [this x] (Multiply (Pw (_fir this) (_sec this))
                                        (diff (Multiply (_sec this) (Ln (_fir this))) x)))))
(def Pw (constructor BinaryCons PwPrototype))

(def Lg)
(def LgPrototype
    (assoc BinaryPrototype :strRepr "lg" :action (fn [x y] (/ (Math/log (Math/abs y)) (Math/log (Math/abs x))))
         :diff (fn [this x] (diff (Divide (Ln (_sec this)) (Ln (_fir this))) x))))
(def Lg (constructor BinaryCons LgPrototype))

(def objOp
    {
    '+ Add
    '- Subtract
    '* Multiply
    '/ Divide
    'negate Negate
    'lg Lg
    'ln Ln
    'pw Pw})

(defn parseObject [expr]
    (cond
        (string? expr) (parseObject (read-string expr))
        (number? expr) (Constant expr)
        (seq? expr) (apply (get objOp (first expr)) (mapv parseObject (rest expr)))
    :else (Variable (str expr))))