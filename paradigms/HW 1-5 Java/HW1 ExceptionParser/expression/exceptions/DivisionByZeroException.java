package expression.exceptions;

public class DivisionByZeroException extends CountingException {
    public DivisionByZeroException() {
        super("division by zero");
    }

    public DivisionByZeroException(String message) {
        super(message);
    }
}
