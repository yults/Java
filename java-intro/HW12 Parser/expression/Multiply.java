package expression;

public class Multiply extends AbstractBinaryAction {
    public Multiply (CommonExpression left, CommonExpression right) {
        super(left, right, "*");
        this.hash += 2;
    }

    @Override
    protected int doAction(int a, int b) {
        return a * b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a * b;
    }
}
