package expression;

public class Minus extends AbstractUnaryAction {
    public Minus (CommonExpression right) {
        super(right, "-");
        this.hash += 4;
    }

    @Override
    protected int doAction(int a) {
        return -a;
    }

    @Override
    protected double doAction(double a) {
        return -a;
    }
}

