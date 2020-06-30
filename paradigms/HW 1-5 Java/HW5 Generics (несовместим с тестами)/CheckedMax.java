package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public class CheckedMax<T> extends AbstractBinaryAction<T> {
    public CheckedMax(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right,"max" );
    }

    public T evaluate(T x, T y, T z, Type<T> type) throws OverflowException, DivisionByZeroException {
        return type.max(getLeft().evaluate(x, y, z, type), getRight().evaluate(x, y, z, type));
    }
}
