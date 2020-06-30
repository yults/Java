package expression;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public class CheckedDivide<T> extends AbstractBinaryAction<T> {
    public CheckedDivide(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, "/");
    }

    public T evaluate(T x, T y, T z, Type<T> type) throws OverflowException, DivisionByZeroException {
        return type.divide(getLeft().evaluate(x, y, z, type), getRight().evaluate(x, y, z, type));
    }
}
