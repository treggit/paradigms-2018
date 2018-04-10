package expression.operators;

public class CheckedOr extends BinaryOperator {
    public CheckedOr(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    protected int calc(int a, int b) {
        return a | b;
    }
}
