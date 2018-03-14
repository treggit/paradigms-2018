package expression;

public class Add extends Operator {

    Add(Expression firstOperand, Expression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int calc(int a, int b) {
        return a + b;
    }
}
