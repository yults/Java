package expression.types;

import expression.exceptions.OverflowException;

public class DoubleType implements Type<Double> {
    private Double x;

    @Override
    public Double evaluator() {
        return this.x;
    }

    @Override
    public Double parseNum(String str) throws OverflowException {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new OverflowException();
        }
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double count(Double x) {
        return (double) Long.bitCount(Double.doubleToLongBits(x));
    }

    @Override
    public Double min(Double x, Double y) {
        return Math.min(x, y);
    }

    @Override
    public Double max(Double x, Double y) {
        return Math.max(x, y);
    }
}
