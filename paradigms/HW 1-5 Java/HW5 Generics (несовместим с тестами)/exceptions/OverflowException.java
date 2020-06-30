package expression.exceptions;

public class OverflowException extends CountException {
    public OverflowException() {
        super("Overflow");
    }
    public OverflowException(String message) {
        super(message);
    }
}
