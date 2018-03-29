package expression;

public class CheckedAnd extends BinaryOperator {
    public CheckedAnd(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    protected int calc(int a, int b) {
        return a & b;
    }
}
