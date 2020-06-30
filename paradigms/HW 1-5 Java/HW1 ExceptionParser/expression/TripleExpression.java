package expression;

import expression.ToMiniString;
import expression.exceptions.CountingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression extends ToMiniString {
    int evaluate(int x, int y, int z) throws CountingException;
}