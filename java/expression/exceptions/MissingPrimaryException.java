package expression.exceptions;

public class MissingPrimaryException extends ParsingException{
    public MissingPrimaryException(final String expr, final int position) {
        super("Primary expression expected at position " + (position + 1)  + '\n' + pointError(expr, position));
    }
}
