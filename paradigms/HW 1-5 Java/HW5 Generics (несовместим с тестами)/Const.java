package expression;

import expression.types.Type;

public class Const<T> implements TripleExpression<T> {
    private T value;

    public Const(T val) {
        this.value = val;
    }

    public T evaluate(T x, T y, T z, Type<T> type) {
        return this.value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
