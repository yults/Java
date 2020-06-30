package expression.parser;

import expression.exceptions.BracketExpectedException;

public interface ExpressionSource {
    boolean hasNext();

    char nextChar() throws BracketExpectedException;
}
