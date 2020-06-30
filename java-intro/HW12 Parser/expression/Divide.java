package expression;

public class Divide extends AbstractBinaryAction {
    public Divide (CommonExpression left, CommonExpression right) {
        super(left, right, "/");
        this.hash += 1;
    }

    @Override
    protected int doAction(int a, int b) {
        return a / b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a / b;
    }
}
