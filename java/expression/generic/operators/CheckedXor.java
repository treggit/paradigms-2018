package expression.generic.operators;

import expression.generic.GenericOperationTable;

public class CheckedXor <T> extends BinaryOperator <T> {
    public CheckedXor(CommonExpression <T> a, CommonExpression <T> b, GenericOperationTable<T> op) {
        super(a, b, op);
    }

    protected T calc(T a, T b) {
        return operation.xor(a, b);
    }
}
