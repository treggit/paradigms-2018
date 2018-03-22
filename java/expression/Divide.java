package expression;

public class Divide extends BinaryOperator {

    public Divide(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a / b;
    }

    protected double calc(double a, double b) {
        return a / b;
    }
}
