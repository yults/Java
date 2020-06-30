package expression;

import expression.exceptions.CountingException;
import expression.exceptions.OverflowException;

public class CheckedAdd extends AbstractBinaryAction {
    public CheckedAdd(CommonExpression left, CommonExpression right) {
        super(left, right, "+");
        this.hash += 5;
    }

    protected void overFlow(int a, int b) throws OverflowException {
        if (a > 0 && Integer.MAX_VALUE - a < b) {
            throw new OverflowException("Max int overflow");
        }
        if (a < 0 && Integer.MIN_VALUE - a > b) {
            throw new OverflowException("Min int overflow");
        }
    }

    @Override
    protected int doAction(int a, int b) throws CountingException {
        overFlow(a, b);
        return a + b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a + b;
    }
}
