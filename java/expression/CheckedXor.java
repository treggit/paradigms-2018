package expression;

public class CheckedXor extends BinaryOperator {
    public CheckedXor(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    protected int calc(int a, int b) {
        return a ^ b;
    }
}
