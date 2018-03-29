package expression.exceptions;

public class UnsupportedOperation extends ParsingException{
    public UnsupportedOperation(final String expr, final int position) {
        super("Unsupported operation at position " + (position + 1)  + '\n' + pointError(expr, position));
    }
}
