package expression.exceptions;

public class UnknownModeException extends Exception {
    public UnknownModeException(String mode) {
        super("Unknown mode: " + mode);
    }
}
