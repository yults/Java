package expression;

public class Variable implements CommonExpression {
    private String sign;
    public Variable (String sign) {
        this.sign = sign;
    }
    public String toString() {
        return sign;
    }
    public int hashCode() {
        return sign.hashCode();
    }
    public int evaluate(int x) {
        return x;
    }
    public double evaluate(double x) {
        return x;
    }
    public boolean equals(Object object) {
        if (object != null && object.getClass() == this.getClass()) {
            return this.sign.equals(object.toString());
        }
        return false;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (sign) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new IllegalArgumentException("Invalid variable");
        }
    }
}
