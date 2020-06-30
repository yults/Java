package expression;

import expression.exceptions.CountingException;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public abstract class AbstractBinaryAction implements CommonExpression {
    private CommonExpression left;
    private CommonExpression right;
    private String action;
    protected int hash;

    public AbstractBinaryAction(CommonExpression left, CommonExpression right, String action){
        this.left = left;
        this.right = right;
        this.action = action;
        this.hash = 73 * (73 * left.hashCode() + right.hashCode());
    }

    protected abstract int doAction(int a, int b) throws CountingException;
    protected abstract double doAction(double a, double b);

    @Override
    public String toString() {
        return "(" + this.left.toString() + " " + this.action + " " + this.right.toString() + ")";
    }

    public int hashCode() {
        return this.hash;
    }
    public int evaluate(int x) throws CountingException {
        return doAction(left.evaluate(x), right.evaluate(x));
    }

    public double evaluate(double x) {
        return doAction(left.evaluate(x), right.evaluate(x));
     }

    @Override
    public int evaluate(int x, int y, int z) throws CountingException {
        return doAction(left.evaluate(x, y, z), right.evaluate(x, y, z)); }

    public boolean equals(Object object) {
        if (object != null && object.getClass() == this.getClass()) {
            return this.left.equals(((AbstractBinaryAction)object).left) && this.right.equals(((AbstractBinaryAction)object).right);
        }
        return false;
     }
}
