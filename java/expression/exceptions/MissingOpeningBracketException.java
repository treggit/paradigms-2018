package expression.exceptions;

public class MissingOpeningBracketException extends ParsingException {
    public MissingOpeningBracketException(final String expr, final int position) {
        super("Opening bracket expected as a pair to the bracket at position " + (position + 1)  + '\n' + pointError(expr, position));
    }
}
