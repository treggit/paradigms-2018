package expression.generic.operators;

import expression.exceptions.EvaluationException;
import expression.generic.GenericOperationTable;

public class CheckedNegate <T> extends UnaryOperator<T> {
    public CheckedNegate(CommonExpression <T> expression, GenericOperationTable <T> op) {
        super(expression, op);
    }

    protected T calc(T a) throws EvaluationException {
        return operation.negate(a);
    }

}
