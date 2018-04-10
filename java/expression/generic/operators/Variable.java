package expression.generic.operators;

import expression.generic.operators.CommonExpression;

public class Variable <T> implements CommonExpression<T> {
    private T value;
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public T evaluate(T x) {
        return value = x;
    }
    
    public T evaluate(T x, T y, T z) {
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
        return value;
    }
}
