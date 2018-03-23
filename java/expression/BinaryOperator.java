package expression;

public abstract class BinaryOperator implements CommonExpression {
    private CommonExpression firstOperand, secondOperand;

    public BinaryOperator(CommonExpression a, CommonExpression b) {
        firstOperand = a;
        secondOperand = b;
    }

    protected abstract int calc(int a, int b);

    public int evaluate(int x) {
        return calc(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return calc(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }
}
