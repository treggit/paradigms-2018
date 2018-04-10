package expression.generic.operators;

import expression.exceptions.EvaluationException;
import expression.generic.GenericOperationTable;
import expression.generic.operators.CommonExpression;

public abstract class UnaryOperator <T> implements CommonExpression<T> {
    private CommonExpression <T> operand;
    protected GenericOperationTable <T> operation;

    public UnaryOperator(CommonExpression <T> expression, GenericOperationTable <T> op) {
        operand = expression;
        operation = op;
    }

    abstract protected T calc(T x) throws EvaluationException;

    public T evaluate(T x) throws EvaluationException {
        return calc(operand.evaluate(x));
    }

    public T evaluate(T x, T y, T z) throws EvaluationException {
        return calc(operand.evaluate(x, y, z));
    }

}
