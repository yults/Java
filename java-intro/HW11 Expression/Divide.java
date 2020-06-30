package expression;

public class Divide extends AbstractBinaryOperator {
    public Divide(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    @Override
    protected String getSignification() {
        return " / ";
    }

    @Override
    protected int calc(int a, int b) {
        return a / b;
    }

    @Override
    protected int getPriority() {
        return 2;
    }

    @Override
    protected boolean isNotCommutative() {
        return true;
    }
}
