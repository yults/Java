package expression.exceptions;

public class UnaryExpectedException extends ParsingException {
    public UnaryExpectedException() {
        super("primary expected");
    }
    public UnaryExpectedException(String message) {
        super(message);
    }
}
