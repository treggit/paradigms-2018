package expression;

public class UnaryMinus extends UnaryOperator {
    public UnaryMinus(CommonExpression expression) {
        super(expression);
    }

    protected int calc(int a) {
        return -a;
    }

    protected double calc(double a) {
        return -a;
    }
}
