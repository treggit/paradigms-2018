package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedNegate extends UnaryOperator {
    public CheckedNegate(CommonExpression expression) {
        super(expression);
    }

    protected int calc(int a) throws EvaluationException {
        try {
            return Math.subtractExact(0, a);
        } catch (ArithmeticException e) {
            throw new OverflowException("subtract");
        }
    }

}
