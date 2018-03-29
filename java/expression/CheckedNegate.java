package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedNegate extends UnaryOperator {
    public CheckedNegate(CommonExpression expression) {
        super(expression);
    }

    protected int calc(int a) throws EvaluationException {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("negation");
        }
        return -a;
    }

}
