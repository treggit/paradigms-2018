package expression;

public abstract class Operator implements Expression {
    protected Expression firstOperand, secondOperand;

    public Operator(Expression a, Expression b) {
        firstOperand = a;
        secondOperand = b;
    }

    protected abstract int calc(int a, int b);

    public int evaluate(int x) {
        return calc(firstOperand.evaluate(x), secondOperand.evaluate(x));
    };

}
