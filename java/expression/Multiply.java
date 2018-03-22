package expression;

public class Multiply extends BinaryOperator {

    public Multiply(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a * b;
    }

    protected double calc(double a, double b) {
        return a * b;
    }
}
