package expression;

public class Variable implements Expression {
    private int value;
    private String name;

    Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x) {
        value = x;
        return value;
    }
}
