package expression;
import expression.exceptions.CountingException;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class CheckedDivide extends AbstractBinaryAction {
    public CheckedDivide(CommonExpression left, CommonExpression right) {
        super(left, right, "/");
        this.hash += 1;
    }
    void overflow(int a, int b) throws OverflowException {
        if (a == Integer.MIN_VALUE && b == -1)
            throw new OverflowException();
    }

    void divisionByZero(int a, int b) throws CountingException {
        if (b == 0)
            throw new DivisionByZeroException();
    }
    @Override
    protected int doAction(int a, int b) throws CountingException {
        overflow(a, b);
        divisionByZero(a, b);
        return a / b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a / b;
    }
}
