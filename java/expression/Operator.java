package expression;

public abstract class Operator implements AnyExpression {
    private AnyExpression firstOperand, secondOperand;

    public Operator(AnyExpression a, AnyExpression b) {
        firstOperand = a;
        secondOperand = b;
    }

    protected abstract int calc(int a, int b);
    protected abstract double calc(double a, double b);

    public int evaluate(int x) {
        return calc(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }

    public double evaluate(double x) {
        return calc(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }

}
