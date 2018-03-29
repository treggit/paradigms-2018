package expression.exceptions;

public class MissingOperatorException extends ParsingException {
    public MissingOperatorException(final String expr, final int position) {
        super("Operator expected at position " + (position + 1)  + '\n' + pointError(expr, position));
    }
}
