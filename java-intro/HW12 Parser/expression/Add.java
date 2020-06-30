package expression;

public class Add extends AbstractBinaryAction {
    public Add(CommonExpression left, CommonExpression right) {
        super(left, right, "+");
        this.hash += 5;
    }

    @Override
    protected int doAction(int a, int b) {
        return a + b;
    }

    @Override
    protected double doAction(double a, double b) {
        return a + b;
    }
}
