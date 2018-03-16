package expression;

public class Subtract extends Operator {

    Subtract(AnyExpression firstOperand, AnyExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a - b;
    }

    protected double calc(double a, double b) {
        return a - b;
    }
}
