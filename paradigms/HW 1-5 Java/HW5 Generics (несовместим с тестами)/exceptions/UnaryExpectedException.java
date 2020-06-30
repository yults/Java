package expression.exceptions;

public class UnaryExpectedException extends ParsingException {
    public UnaryExpectedException() {
        super("Unary expected");
    }
    public UnaryExpectedException(String message) {
        super(message);
    }
}
