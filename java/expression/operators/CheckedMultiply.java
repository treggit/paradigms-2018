package expression.operators;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedMultiply extends BinaryOperator {

    public CheckedMultiply(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    private void check(int a, int b) {
        if (a > 0 && b > 0 && Integer.MAX_VALUE / b < a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b > 0 && Integer.MIN_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b < 0 && Integer.MAX_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a > 0 && b < 0 && Integer.MIN_VALUE / a > b) {
            throw new OverflowException("multiply");
        }
    }

    protected int calc(int a, int b) throws EvaluationException {
        check(a, b);
        return a * b;
    }

}
