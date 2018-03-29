package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedMultiply extends BinaryOperator {

    public CheckedMultiply(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }
    protected int calc(int a, int b) throws EvaluationException {
        try {
            return Math.multiplyExact(a, b);
        } catch (ArithmeticException e) {
            throw new OverflowException("multiply");
        }
    }

}
