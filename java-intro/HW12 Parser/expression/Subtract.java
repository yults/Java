package expression;

public class Subtract extends AbstractBinaryAction {
    public Subtract (CommonExpression left, CommonExpression right) {
        super(left, right, "-");
        this.hash += 8;
    }

    @Override
    protected int doAction(int a, int b) {
        return a - b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a - b;
    }
}
