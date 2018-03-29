package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedAdd extends BinaryOperator {

    public CheckedAdd(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) throws EvaluationException{
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            throw new OverflowException("add");
        }
    }
}
