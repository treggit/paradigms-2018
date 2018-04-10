package expression.generic.operators;

import expression.exceptions.EvaluationException;
import expression.generic.GenericOperationTable;

public class CheckedAdd <T> extends BinaryOperator <T> {

    public CheckedAdd(CommonExpression <T> firstOperand, CommonExpression <T> secondOperand, GenericOperationTable <T> op) {
        super(firstOperand, secondOperand, op);
    }

    protected T calc(T a, T b) throws EvaluationException{
        return operation.add(a, b);
    }
}
