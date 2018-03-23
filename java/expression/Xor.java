package expression;

public class Xor extends BinaryOperator {
    public Xor(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    protected int calc(int a, int b) {
        return a ^ b;
    }
}
