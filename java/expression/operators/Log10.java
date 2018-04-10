package expression.operators;
import expression.exceptions.EvaluationException;
import expression.exceptions.IllegalLogValue;

public class Log10 extends UnaryOperator{
    public Log10(CommonExpression expression) {
        super(expression);
    }

    protected int calc(int x) throws EvaluationException {
        if (x <= 0) {
            throw new IllegalLogValue("Positive log value expected");
        }

        int log = 0;
        while (x > 0) {
            log++;
            x /= 10;
        }
        return (log == 0 ? 0 : log - 1);
    }
}
