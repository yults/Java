package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public class CheckedNegate<T> extends AbstractUnaryAction<T> {
    public CheckedNegate(TripleExpression<T> right) {
        super(right, "-");
    }

    public T evaluate(T x, T y, T z, Type<T> type) throws OverflowException, DivisionByZeroException {
        return type.negate(getRight().evaluate(x, y, z, type));
    }

}



