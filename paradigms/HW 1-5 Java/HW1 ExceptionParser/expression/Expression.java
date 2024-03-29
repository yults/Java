package expression;

import expression.exceptions.CountingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression extends ToMiniString {
    int evaluate(int x) throws CountingException;
}
