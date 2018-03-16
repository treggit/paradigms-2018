package expression;

public class Const implements AnyExpression {
    private Number value;

    Const(Number value) {
        this.value = value;
    }

    public int evaluate(int x) {
        return value.intValue();
    }

    public double evaluate(double x) {
        return value.doubleValue();
    }
}
