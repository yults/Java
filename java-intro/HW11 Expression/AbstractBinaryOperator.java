package expression;

public abstract class AbstractBinaryOperator implements CommonExpression {
    private CommonExpression left, right;

    public AbstractBinaryOperator(CommonExpression left, CommonExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract String getSignification();

    protected abstract int calc(int a, int b);

    protected abstract int getPriority();

    protected abstract boolean isNotCommutative();

    @Override
    public int evaluate(int x) {
        return calc(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + left + getSignification() + right + ")";
    }

    private String getExpression(Expression expression, boolean needBrackets) {
        return (needBrackets ? "(" : "") + expression.toMiniString() + (needBrackets ? ")" : "");
    }

    private boolean needOrderingBrackets(Expression expression) {
        return expression instanceof AbstractBinaryOperator
                && (((AbstractBinaryOperator) expression).isNotCommutative() || this.isNotCommutative())
                && ((AbstractBinaryOperator) expression).getPriority() <= this.getPriority();
    }

    private boolean needPriorityBrackets(Expression expression) {
        return expression instanceof AbstractBinaryOperator
                && (((AbstractBinaryOperator) expression).getPriority() < this.getPriority());
    }

    @Override
    public String toMiniString() {
        return getExpression(left, needPriorityBrackets(left))
                + getSignification()
                + getExpression(right, needPriorityBrackets(right) || needOrderingBrackets(right));
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AbstractBinaryOperator that = (AbstractBinaryOperator) object;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return ((left.hashCode() * 31) + getClass().hashCode()) * 31 + right.hashCode();
    }
}
