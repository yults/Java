package expression;

import expression.exceptions.CountingException;
import expression.exceptions.OverflowException;

public class CheckedMultiply extends AbstractBinaryAction {
    public CheckedMultiply(CommonExpression left, CommonExpression right) {
        super(left, right, "*");
        this.hash += 2;
    }

    private int abs(int a) {
        if (a < 0)
            return -a;
        return a;
    }

    private void overflow(int a, int b) throws OverflowException {
        if (a > 0 && b > 0 || a < 0 && b < 0) {
            if (a == Integer.MIN_VALUE || b == Integer.MIN_VALUE) {
                throw new OverflowException();
            }
            if (b == Integer.MAX_VALUE || a == Integer.MAX_VALUE)
                if (a != 1 && b != 1) {
                    throw new OverflowException();
                }
            a = abs(a);
            b = abs(b);
            if (a > Integer.MAX_VALUE / b) {
                throw new OverflowException();
            }
        } else {
            if (a != 0 && b != 0) {
                if (b > 0 && a < Integer.MIN_VALUE / b || a > 0 && b < Integer.MIN_VALUE / a)
                    throw new OverflowException();
            }
        }
    }

    @Override
    protected int doAction(int a, int b) throws CountingException {
        overflow(a, b);
        return a * b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a * b;
    }
}
