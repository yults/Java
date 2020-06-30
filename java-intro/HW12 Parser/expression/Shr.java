package expression;

public class Shr extends AbstractBinaryAction {
    public Shr(CommonExpression left, CommonExpression right) {
        super(left, right, ">>");
    }

    @Override
    protected int doAction(int a, int b) {
        return a >> b;
    }

    @Override
    protected double doAction(double a, double b) {
        return 0;
    }
}
