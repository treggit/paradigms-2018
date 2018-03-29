package expression.exceptions;

public class OverflowException extends EvaluationException{
    public OverflowException(final String operation) {
        super("Overflow detected during " + operation + " operation");
    }
}
