package expression.exceptions;

public class ParsingException extends RuntimeException {
    public ParsingException(final String message) {
        super(message);
    }

    static protected String pointError(final String expr, final int position) {
        StringBuilder ptr = new StringBuilder(expr);
        ptr.append('\n');
        for (int i = 0; i < position; i++) {
            ptr.append('-');
        }
        ptr.append('^');
        for (int i = position; i < expr.length() - 1; i++) {
            ptr.append('-');
        }
        return ptr.toString();
    }
}
