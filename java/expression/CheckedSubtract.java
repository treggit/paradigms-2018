package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedSubtract extends BinaryOperator {

    public CheckedSubtract(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) throws EvaluationException {
        if ((b < 0 && Integer.MAX_VALUE + b < a) || (b > 0 && Integer.MIN_VALUE + b > a)) {
            throw new OverflowException("subtract");
        }
        return a - b;
    }

}
