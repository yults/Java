package expression;

public class Add extends AbstractBinaryOperator {
    public Add(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    @Override
    protected String getSignification() {
        return " + ";
    }

    @Override
    protected int calc(int a, int b) {
        return a + b;
    }

    @Override
    protected int getPriority() {
        return 1;
    }

    @Override
    protected boolean isNotCommutative() {
        return false;
    }
}
