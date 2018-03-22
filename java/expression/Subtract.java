package expression;

public class Subtract extends BinaryOperator {

    public Subtract(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a - b;
    }

    protected double calc(double a, double b) {
        return a - b;
    }
}
