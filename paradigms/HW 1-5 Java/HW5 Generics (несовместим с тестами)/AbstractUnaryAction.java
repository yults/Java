package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;
import expression.types.Type;

public abstract class AbstractUnaryAction<T> implements TripleExpression<T> {
    protected TripleExpression<T> right;
    private String s;

    protected AbstractUnaryAction(TripleExpression<T> right, String s) {
        this.right = right;
        this.s = s;
    }

    public TripleExpression<T> getRight() {
        return right;
    }

    public T evaluate(T x, T y, T z, Type<T> type) throws OverflowException, DivisionByZeroException {
        return  type.evaluator();
    }

    public String toString() {
        return "("  + s + " " + right + ")";
    }

}
