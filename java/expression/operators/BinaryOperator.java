package expression.operators;

import expression.exceptions.EvaluationException;

public abstract class BinaryOperator implements CommonExpression {
    private CommonExpression firstOperand, secondOperand;

    public BinaryOperator(CommonExpression a, CommonExpression b) {
        firstOperand = a;
        secondOperand = b;
    }

    protected abstract int calc(int a, int b) throws EvaluationException;

    public int evaluate(int x) throws EvaluationException{
        return calc(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }

    public int evaluate(int x, int y, int z) throws EvaluationException {
        return calc(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }
}
