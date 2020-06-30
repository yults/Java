package expression.types;

import expression.exceptions.*;

import java.math.BigInteger;

public class BigIntegerType implements Type<BigInteger> {
    private BigInteger x;

    @Override
    public BigInteger evaluator() {
        return this.x;
    }

    @Override
    public BigInteger parseNum(String str) throws OverflowException {
        try {
            return new BigInteger(str);
        } catch (NumberFormatException e) {
            throw new OverflowException();
        }
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger sub(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger mul(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) throws DivisionByZeroException {
        if (y.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException();
        }
        return x.divide(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger count(BigInteger x) {
        return BigInteger.valueOf(x.bitCount());
    }

    @Override
    public BigInteger min(BigInteger x, BigInteger y) {
        return x.min(y);
    }

    @Override
    public BigInteger max(BigInteger x, BigInteger y) {
        return x.max(y);
    }
}
