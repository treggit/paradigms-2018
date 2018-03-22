package expression;

public abstract class UnaryOperator implements CommonExpression {
    private CommonExpression operand;

    public UnaryOperator(CommonExpression expression) {
        operand = expression;
    }

    abstract protected int calc(int x);
    abstract protected double calc(double x);

    public int evaluate(int x) {
        return calc(operand.evaluate(x));
    }

    public double evaluate(double x) {
        return calc(operand.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return calc(operand.evaluate(x, y, z));
    }

}
