package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public abstract class AbstractBinaryAction<T> implements TripleExpression<T> {
    private TripleExpression<T> left;
    private TripleExpression<T> right;
    private String s;

    public TripleExpression<T> getLeft() {
        return left;
    }
    public TripleExpression<T> getRight() {
        return right;
    }

    public AbstractBinaryAction(TripleExpression<T> left, TripleExpression<T> right, String s) {
        this.left = left;
        this.right = right;
        this.s = s;
    }

    public T evaluate(T x, T y, T z,  Type<T> type) throws OverflowException, DivisionByZeroException {
        return type.evaluator();
    }

    public String toString() {
        return "(" + left + " " + s + " " + right + ")";
    }
}
