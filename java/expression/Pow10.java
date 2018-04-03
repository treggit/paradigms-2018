package expression;

import expression.exceptions.EvaluationException;
import expression.exceptions.IllegalDegreeValue;
import expression.exceptions.OverflowException;

public class Pow10 extends UnaryOperator{
    public Pow10(CommonExpression expression) {
        super(expression);
    }

    void check(int a, int b) {
        if (a > 0 && b > 0 && Integer.MAX_VALUE / b < a) {
            throw new OverflowException("pow");
        }
    }

    private int binPow(int x, int deg) {
        int res = 1;
        while (deg > 0) {
            if (deg % 2 == 1) {
                check(res, x);
                res *= x;
            }
            deg >>= 1;
            if (deg > 0) {
                check(x, x);
                x *= x;
            }
        }
        return res;
    }

    protected int calc(int x) {
        if (x < 0) {
            throw new IllegalDegreeValue("Positive degree value expected");
        }
        return binPow(10, x);
    }
}
