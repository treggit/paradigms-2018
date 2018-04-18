package expression.generic;

import expression.exceptions.*;

public class IntegerOperation implements GenericOperationTable <Integer> {

    private boolean checked;

    public IntegerOperation(boolean checked) {
        this.checked = checked;
    }

    private void check(final String op, final Integer a, final Integer b) {
        if (!checked) {
            return;
        }
        switch (op) {
            case "add":
                checkAdd(a, b);
                break;
            case "sub":
                checkSubtract(a, b);
                break;
            case "mul":
                checkMultiply(a, b);
                break;
            case "div":
                checkDivide(a, b);
                break;
        }
    }

    private void check(final String op, final Integer a) {
        if (!checked) {
            return;
        }
        switch (op) {
            case "neg":
                checkNegation(a);
                break;
            case "binPow":
                checkBinPow(a);
            case "log10":
                checkLog10(a);
        }
    }

    private void checkAdd(final Integer a, final Integer b) {
        if ((b < 0 && Integer.MIN_VALUE - b > a) || (b > 0 && Integer.MAX_VALUE - b < a)) {
            throw new OverflowException("add");
        }
    }

    @Override
    public Integer add(final Integer a, final Integer b) throws EvaluationException {
        check("add", a, b);
        return a + b;
    }


    private void checkSubtract(final Integer a, final Integer b) {
        if ((b < 0 && Integer.MAX_VALUE + b < a) || (b > 0 && Integer.MIN_VALUE + b > a)) {
            throw new OverflowException("subtract");
        }
    }

    @Override
    public Integer subtract(final Integer a, final Integer b) throws EvaluationException {
        check("sub", a, b);
        return a - b;
    }


    private void checkMultiply(final Integer a, final Integer b) {
        if (a > 0 && b > 0 && Integer.MAX_VALUE / b < a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b > 0 && Integer.MIN_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b < 0 && Integer.MAX_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a > 0 && b < 0 && Integer.MIN_VALUE / a > b) {
            throw new OverflowException("multiply");
        }
    }

    @Override
    public Integer multiply(final Integer a, final Integer b) throws EvaluationException {
        check("mul", a, b);
        return a * b;
    }

    private void checkDivide(final Integer a, final Integer b) {

        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("divide");
        }
    }


    @Override
    public Integer divide(final Integer a, final Integer b) throws EvaluationException {
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        check("div", a, b);
        return a / b;
    }

    private void checkNegation(final Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("negation");
        }
    }

    @Override
    public Integer negate(final Integer a) throws EvaluationException {
        check("neg", a);
        return -a;
    }

    @Override
    public Integer and(final Integer a, final Integer b) throws EvaluationException {
        return a & b;
    }

    @Override
    public Integer or(final Integer a, final Integer b) throws EvaluationException {
        return a | b;
    }

    @Override
    public Integer xor(final Integer a, final Integer b) throws EvaluationException {
        return a ^ b;
    }

    private void checkBinPow(final Integer a) {
        if (a < 0) {
            throw new IllegalDegreeValue("Positive degree value expected");
        }
    }

    private int binPow(Integer x, Integer deg) {
        int res = 1;
        while (deg > 0) {
            if (deg % 2 == 1) {
                check("mul", res, x);
                res *= x;
            }
            deg >>= 1;
            if (deg > 0) {
                check("mul", x, x);
                x *= x;
            }
        }
        return res;
    }

    @Override
    public Integer pow10(final Integer a) throws EvaluationException {
        check("binPow", a);
        return binPow(10, a);
    }

    private void checkLog10(final Integer a) {
        if (a <= 0) {
            throw new IllegalLogValue("Positive log value expected");
        }
    }

    private Integer log(Integer a, final Integer base) {
        int log = 0;
        while (a > 0) {
            log++;
            a /= base;
        }
        return (log == 0 ? 0 : log - 1);
    }

    @Override
    public Integer log10(final Integer a) throws EvaluationException {
        check("log10", a);
        return log(a, 10);
    }

    @Override
    public Integer parseNumber(String number) {
        return Integer.parseInt(number);
    }

    @Override
    public Integer valueOf(int number) {
        return number;
    }
}
