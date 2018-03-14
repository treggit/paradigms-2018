package expression;

public class Subtract extends Operator {

    Subtract(Expression firstOperand, Expression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a - b;
    }
}
