package expression;

import expression.exceptions.CountingException;

public abstract class AbstractUnaryAction implements CommonExpression {
    protected CommonExpression right;
    private String action;
    protected int hash;

    protected AbstractUnaryAction(CommonExpression right, String action) {
        this.right = right;
        this.action = action;
        this.hash = 71 * (71 + right.hashCode());
    }

    protected abstract int doAction(int a) throws CountingException;
    protected abstract double doAction(double a);

    @Override
    public String toString() {
        return "(" + this.action + " " + this.right.toString() + ")";
    }

    public int hashCode() {
        return this.hash;
    }
    public int evaluate(int x) throws CountingException {
        return doAction(right.evaluate(x));
    }

    public double evaluate(double x) {
        return doAction(right.evaluate(x));
    }
    public int evaluate(int x, int y, int z) throws CountingException {
        return doAction(right.evaluate(x, y, z));
    }


    public boolean equals(Object object) {
        if (object != null && object.getClass() == this.getClass()) {
            return this.right.equals(((AbstractUnaryAction)object).right);
        }
        return false;
    }
}
