package expression.exceptions;

public class OverflowException extends CountingException {
    public OverflowException() {
        super("overflow");
    }

    public OverflowException(String message) {
        super(message);
    }
}
