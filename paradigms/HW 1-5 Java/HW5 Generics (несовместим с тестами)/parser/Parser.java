package expression.parser;

import expression.TripleExpression;
import expression.exceptions.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser{
    TripleExpression parse(String expression) throws UnknownSymbolException, BracketExpectedException, OverflowException, UnaryExpectedException;
}
