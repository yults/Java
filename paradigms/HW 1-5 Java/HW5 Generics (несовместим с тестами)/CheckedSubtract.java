package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public class CheckedSubtract<T> extends AbstractBinaryAction<T> {
    public CheckedSubtract(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, "-");
    }

    public T evaluate(T x, T y, T z, Type<T> type) throws OverflowException, DivisionByZeroException {
        return type.sub(getLeft().evaluate(x, y, z, type), getRight().evaluate(x, y, z, type));
    }
}
