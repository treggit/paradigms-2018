package expression.generic.operators;

import expression.exceptions.EvaluationException;
import expression.generic.GenericOperationTable;

public abstract class BinaryOperator <T> implements CommonExpression <T> {
    private CommonExpression <T> firstOperand, secondOperand;
    protected GenericOperationTable <T> operation;

    public BinaryOperator(CommonExpression <T> a, CommonExpression <T> b, GenericOperationTable <T> op) {
        firstOperand = a;
        secondOperand = b;
        operation = op;
    }

    protected abstract T calc(T a, T b) throws EvaluationException;

    public T evaluate(T x) throws EvaluationException{
        return calc(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }

    public T evaluate(T x, T y, T z) throws EvaluationException {
        return calc(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }
}
