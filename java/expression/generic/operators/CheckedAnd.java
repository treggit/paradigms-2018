package expression.generic.operators;

import expression.generic.GenericOperationTable;

public class CheckedAnd <T> extends BinaryOperator <T> {
    public CheckedAnd(CommonExpression <T> a, CommonExpression <T> b, GenericOperationTable <T> op) {
        super(a, b, op);
    }

    protected T calc(T a, T b) {
        return operation.and(a, b);
    }
}
