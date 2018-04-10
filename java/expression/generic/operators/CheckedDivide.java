package expression.generic.operators;

import expression.exceptions.EvaluationException;
import expression.generic.GenericOperationTable;

public class CheckedDivide <T> extends BinaryOperator <T> {

    public CheckedDivide(CommonExpression <T> a, CommonExpression <T> b, GenericOperationTable<T> op) {
        super(a, b, op);
    }  

    protected T calc(T a, T b) throws EvaluationException {
        return operation.divide(a, b);
    }

}
