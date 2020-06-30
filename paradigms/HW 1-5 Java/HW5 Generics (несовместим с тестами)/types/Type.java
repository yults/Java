package expression.types;

import expression.exceptions.*;

public interface Type<T> {
    T parseNum(String str) throws OverflowException;
    T evaluator();
    T add(T x, T y) throws OverflowException;
    T sub(T x, T y) throws OverflowException;
    T mul(T x, T y) throws OverflowException;
    T divide(T x, T y) throws OverflowException, DivisionByZeroException;
    T negate(T x) throws OverflowException;
    T count(T x);
    T min(T x, T y);
    T max(T x, T y);
}
