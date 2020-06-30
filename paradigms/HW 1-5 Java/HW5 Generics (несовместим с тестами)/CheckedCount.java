package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public class CheckedCount<T> extends AbstractUnaryAction<T> {
    public CheckedCount(TripleExpression<T> expression) {
        super(expression, "count");
    }

    public T evaluate(T x, T y, T z, Type<T> type) throws OverflowException, DivisionByZeroException {
        return type.count(getRight().evaluate(x, y, z, type));
    }
}
