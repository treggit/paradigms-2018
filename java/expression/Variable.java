package expression;

public class Variable implements AnyExpression {
    private Number value;
    private String name;

    Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x) {
        value = x;
        return value.intValue();
    }

    public double evaluate(double x) {
        value = x;
        return value.doubleValue();
    }
}
