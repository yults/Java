package expression.exceptions;

public class UnknownSymbolException extends ParsingException {
    public UnknownSymbolException() {
        super("Unknown symbol");
    }
}
