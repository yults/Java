(defn constant [val] (fn [x] val))
(defn variable [name] (fn [x] x name)))
(defn operation [f] (fn [& operands] (fn [args] (apply f (map (fn [x] (x args)) operands)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn [a b] (/ (double a) (double b)))))

(def operations {'+ add, '- subtract, '* multiply, '/ divide, 'min min, 'max max})

(defn parseFunction [expr]
  (cond
    (string? expr) (parseFunction (read-string expr))
    (number? expr) (constant expr)
    (symbol? expr) (variable (str expr))
    (seq? expr) (apply (get operations (first expr)) (map parseFunction (rest expr)))))