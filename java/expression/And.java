package expression;

public class And extends BinaryOperator {
    public And(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    protected int calc(int a, int b) {
        return a & b;
    }
}
