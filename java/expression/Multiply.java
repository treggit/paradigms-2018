package expression;

public class Multiply extends Operator {

    Multiply(Expression firstOperand, Expression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a * b;
    }
}
