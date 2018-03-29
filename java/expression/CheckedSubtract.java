package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedSubtract extends BinaryOperator {

    public CheckedSubtract(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) throws EvaluationException {
        try {
            return Math.subtractExact(a, b);
        } catch (ArithmeticException e) {
            throw new OverflowException("multiply");
        }
    }

}
