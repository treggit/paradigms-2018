package expression.generic.operators;

import expression.exceptions.EvaluationException;
import expression.generic.GenericOperationTable;

public class CheckedSubtract <T> extends BinaryOperator <T> {

    public CheckedSubtract(CommonExpression <T> a, CommonExpression <T> b, GenericOperationTable<T> op) {
        super(a, b, op);
    }

    protected T calc(T a, T b) throws EvaluationException {
        return operation.subtract(a, b);
    }

}
