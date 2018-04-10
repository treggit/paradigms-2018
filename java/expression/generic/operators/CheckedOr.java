package expression.generic.operators;

import expression.generic.GenericOperationTable;

public class CheckedOr <T> extends BinaryOperator <T> {
    public CheckedOr(CommonExpression <T> a, CommonExpression <T> b, GenericOperationTable<T> op) {
        super(a, b, op);
    }
    
    protected T calc(T a, T b) {
        return operation.or(a, b);
    }
}
