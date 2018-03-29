package expression;

import expression.exceptions.EvaluationException;

public abstract class UnaryOperator implements CommonExpression {
    private CommonExpression operand;

    public UnaryOperator(CommonExpression expression) {
        operand = expression;
    }

    abstract protected int calc(int x) throws EvaluationException;

    public int evaluate(int x) throws EvaluationException {
        return calc(operand.evaluate(x));
    }

    public int evaluate(int x, int y, int z) throws EvaluationException {
        return calc(operand.evaluate(x, y, z));
    }

}
