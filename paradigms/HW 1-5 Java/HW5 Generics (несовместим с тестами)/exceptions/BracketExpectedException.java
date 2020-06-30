package expression.exceptions;

public class BracketExpectedException extends ParsingException {
    public BracketExpectedException() {
        super("bracket expected");
    }

    public BracketExpectedException(String message) {
        super(message);
    }


}