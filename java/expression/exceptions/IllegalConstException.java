package expression.exceptions;

public class IllegalConstException extends ParsingException {
    public IllegalConstException(final String expr, final int position) {
        super("Illegal const value at position " + (position + 1) + '\n' + pointError(expr, position));
    }
}
