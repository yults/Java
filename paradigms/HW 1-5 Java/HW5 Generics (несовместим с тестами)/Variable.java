package expression;

import expression.types.Type;

public class Variable<T> implements TripleExpression<T> {
    private String sign;
    public Variable (String sign) {
        this.sign = sign;
    }
    public String toString() {
        return sign;
    }


    public T evaluate(T x, T y, T z, Type<T> type) {
        switch (sign) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new IllegalArgumentException("Invalid variable");
        }
    }

}
