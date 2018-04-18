package expression.exceptions;

public class MissingClosingBracketException extends ParsingException {
    public MissingClosingBracketException(final String expr, final int position) {
        super("Closing bracket expected as a pair to the bracket at position " + (position + 1)  + '\n' + pointError(expr, position));
    }
}
