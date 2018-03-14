package expression;

public class Divide extends Operator {

    Divide(Expression firstOperand, Expression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a / b;
    }
}
