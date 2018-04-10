package expression.generic.operators;

public class Const <T> implements CommonExpression <T> {
    private T value;

    public Const(T value) {
        this.value = value;
    }

    public T evaluate(T x) {
        return value;
    }
    
    public T evaluate(T x, T y, T z) {
        return value;
    }
}
