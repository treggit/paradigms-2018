package expression.generic.operators;

import expression.exceptions.EvaluationException;
import expression.generic.GenericOperationTable;

public class Log10 <T> extends UnaryOperator<T> {
    public Log10 (CommonExpression<T> expression, GenericOperationTable<T> op) {
        super(expression, op);
    }

    protected T calc(T x) throws EvaluationException {
        return operation.log10(x);
    }
}
