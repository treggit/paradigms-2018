package expression.operators;

public class Variable implements CommonExpression {
    private Number value;
    private String name;

    public Variable(String name) {
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

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                value = x;
                break;
            case "y":
                value = y;
                break;
            case "z":
                value = z;
                break;
        }
        return value.intValue();
    }
}
