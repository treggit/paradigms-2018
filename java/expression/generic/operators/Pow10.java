package expression.generic.operators;

import expression.generic.GenericOperationTable;

public class Pow10 <T> extends UnaryOperator<T> {
    public Pow10(CommonExpression<T> expression, GenericOperationTable<T> op) {
        super(expression, op);
    }

    protected T calc(T x) {
        return operation.pow10(x);
    }
}
