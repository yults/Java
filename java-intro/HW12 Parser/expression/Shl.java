package expression;

public class Shl extends AbstractBinaryAction {
    public Shl(CommonExpression left, CommonExpression right) {
        super(left, right, "<<");
    }

    @Override
    protected int doAction(int a, int b) {
        return a << b;
    }

    @Override
    protected double doAction(double a, double b) {
        return 0;
    }
}

