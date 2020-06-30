package expression;

import expression.exceptions.CountingException;
import expression.exceptions.OverflowException;

public class CheckedSubtract extends AbstractBinaryAction {
    public CheckedSubtract(CommonExpression left, CommonExpression right) {
        super(left, right, "-");
        this.hash += 8;
    }


    protected void overflow(int a, int b) throws OverflowException {
        if (a >= 0 && b < 0 && Integer.MAX_VALUE + b < a) {
            throw new OverflowException("Max int value");
        }
        if (a < 0 && b > 0 && Integer.MIN_VALUE + b > a) {
            throw new OverflowException("Min int value");
        }
    }

    @Override
    protected int doAction(int a, int b) throws CountingException {
        overflow(a, b);
        return a - b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a - b;
    }
}
