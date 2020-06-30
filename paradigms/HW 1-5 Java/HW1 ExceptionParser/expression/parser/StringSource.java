package expression.parser;

import expression.exceptions.BracketExpectedException;

import java.util.Stack;

public class StringSource implements ExpressionSource {
    public final String expression;
    public int pos;
    Stack<Character> brackets = new Stack<Character>();

    public StringSource(final String expression) {
        this.expression = expression;
        this.pos = 0;
    }

    private void brackerCheck() throws BracketExpectedException{
        if (expression.charAt(pos) == '('){
            brackets.push('(');
        }
        if (expression.charAt(pos) == ')') {
            if (brackets.isEmpty())
                throw new BracketExpectedException();
            brackets.pop();
        }
        if (pos == expression.length() && !brackets.isEmpty())
            throw new BracketExpectedException();
    }
    @Override
    public boolean hasNext() {
        return pos < expression.length();
    }

    @Override
    public char nextChar() throws BracketExpectedException{
        //brackerCheck();
        return expression.charAt(pos++);
    }
}
