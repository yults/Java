package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public class CheckedMin<T> extends AbstractBinaryAction<T> {
    public CheckedMin(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, "min");
    }

    public T evaluate(T x, T y, T z, Type<T> type) throws OverflowException, DivisionByZeroException {
        return type.min(getLeft().evaluate(x, y, z, type), getRight().evaluate(x, y, z, type));
    }
}
