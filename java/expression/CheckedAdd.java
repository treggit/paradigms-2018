package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.OverflowException;

public class CheckedAdd extends BinaryOperator {

    public CheckedAdd(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) throws EvaluationException{
        if ((b < 0 && Integer.MIN_VALUE - b > a) || (b > 0 && Integer.MAX_VALUE - b < a)) {
            throw new OverflowException("add");
        }
        return a + b;
    }
}
