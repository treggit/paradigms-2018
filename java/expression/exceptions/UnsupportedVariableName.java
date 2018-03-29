package expression.exceptions;

public class UnsupportedVariableName extends ParsingException {
    public UnsupportedVariableName(final String expr, final int position) {
        super("Unsupported variable name at position " + (position + 1)  + '\n' + pointError(expr, position));
    }
}
