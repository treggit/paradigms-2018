package expression.operators;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedDivide extends BinaryOperator {

    public CheckedDivide(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) throws EvaluationException {
        if (b == 0) {
            throw new DivisionByZeroException();
        }

        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("divide");
        }
        return a / b;
    }

}
