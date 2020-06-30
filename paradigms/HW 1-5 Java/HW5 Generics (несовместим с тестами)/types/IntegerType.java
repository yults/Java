package expression.types;

import expression.exceptions.*;

public class IntegerType implements Type<Integer> {
    private Integer x;

    @Override
    public Integer evaluator() {
        return this.x;
    }

    @Override
    public Integer parseNum(String str) throws OverflowException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new OverflowException();
        }
    }

    @Override
    public Integer add(Integer x, Integer y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException("Max overflow");
        } else if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException("Min overflow");
        }
        return x + y;
    }

    @Override
    public Integer sub(Integer x, Integer y) throws OverflowException {
        if (y < 0 && Integer.MAX_VALUE + y < x) {
            throw new OverflowException("Max overflow");
        } else if (y > 0 && Integer.MIN_VALUE + y > x) {
            throw new OverflowException("Min overflow");
        }
        return x - y;
    }
    private int abs(int a) {
        if (a < 0)
            return -a;
        return a;
    }

    private void overflow(int a, int b) throws OverflowException {
        if (a > 0 && b > 0 || a < 0 && b < 0) {
            if (a == Integer.MIN_VALUE || b == Integer.MIN_VALUE) {
                throw new OverflowException();
            }
            if (b == Integer.MAX_VALUE || a == Integer.MAX_VALUE)
                if (a != 1 && b != 1) {
                    throw new OverflowException();
                }
            a = abs(a);
            b = abs(b);
            if (a > Integer.MAX_VALUE / b) {
                throw new OverflowException();
            }
        } else {
            if (a != 0 && b != 0) {
                if (b > 0 && a < Integer.MIN_VALUE / b || a > 0 && b < Integer.MIN_VALUE / a)
                    throw new OverflowException();
            }
        }
    }

    @Override
    public Integer mul(Integer x, Integer y) throws OverflowException {
        overflow(x, y);
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) throws OverflowException, DivisionByZeroException {
        if (y == 0) {
            throw new DivisionByZeroException();
        }
        if (y == -1 && x == Integer.MIN_VALUE) {
            throw new OverflowException("Max overflow");
        }
        return x / y;
    }

    @Override
    public Integer negate(Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Max overflow");
        }
        return -x;
    }

    @Override
    public Integer count(Integer x) {
        return Integer.bitCount(x);
    }

    @Override
    public Integer min(Integer x, Integer y) {
        return Math.min(x, y);
    }

    @Override
    public Integer max(Integer x, Integer y) {
        return Math.max(x, y);
    }
}
