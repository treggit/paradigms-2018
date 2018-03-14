package expression;

public class Const implements Expression {
    private int value;

    Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }
}
