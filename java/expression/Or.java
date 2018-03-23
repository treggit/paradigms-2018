package expression;

public class Or extends BinaryOperator {
    public Or(CommonExpression a, CommonExpression b) {
        super(a, b);
    }

    protected int calc(int a, int b) {
        return a | b;
    }
}
