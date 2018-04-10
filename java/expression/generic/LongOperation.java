package expression.generic;

import expression.exceptions.*;

public class LongOperation implements GenericOperationTable <Long> {

    private boolean checked;

    public LongOperation(boolean checked) {
        this.checked = checked;
    }

    private void check(final String op, final Long a, final Long b) {
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

    private void check(final String op, final Long a) {
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

    private void checkAdd(final Long a, final Long b) {
        if ((b < 0 && Long.MIN_VALUE - b > a) || (b > 0 && Long.MAX_VALUE - b < a)) {
            throw new OverflowException("add");
        }
    }

    @Override
    public Long add(final Long a, final Long b) throws EvaluationException {
        check("add", a, b);
        return a + b;
    }


    private void checkSubtract(final Long a, final Long b) {
        if ((b < 0 && Long.MAX_VALUE + b < a) || (b > 0 && Long.MIN_VALUE + b > a)) {
            throw new OverflowException("subtract");
        }
    }

    @Override
    public Long subtract(final Long a, final Long b) throws EvaluationException {
        check("sub", a, b);
        return a - b;
    }


    private void checkMultiply(final Long a, final Long b) {
        if (a > 0 && b > 0 && Long.MAX_VALUE / b < a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b > 0 && Long.MIN_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b < 0 && Long.MAX_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a > 0 && b < 0 && Long.MIN_VALUE / a > b) {
            throw new OverflowException("multiply");
        }
    }

    @Override
    public Long multiply(final Long a, final Long b) throws EvaluationException {
        check("mul", a, b);
        return a * b;
    }

    private void checkDivide(final Long a, final Long b) {

        if (a == Long.MIN_VALUE && b == -1) {
            throw new OverflowException("divide");
        }
    }


    @Override
    public Long divide(final Long a, final Long b) throws EvaluationException {
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        check("div", a, b);
        return a / b;
    }

    private void checkNegation(final Long a) {
        if (a == Long.MIN_VALUE) {
            throw new OverflowException("negation");
        }
    }

    @Override
    public Long negate(final Long a) throws EvaluationException {
        check("neg", a);
        return -a;
    }

    @Override
    public Long and(final Long a, final Long b) throws EvaluationException {
        return a & b;
    }

    @Override
    public Long or(final Long a, final Long b) throws EvaluationException {
        return a | b;
    }

    @Override
    public Long xor(final Long a, final Long b) throws EvaluationException {
        return a ^ b;
    }

    void checkBinPow(final Long a) {
        if (a < 0) {
            throw new IllegalDegreeValue("Positive degree value expected");
        }
    }

    private Long binPow(Long x, Long deg) {
        Long res = 1L;
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
    public Long pow10(final Long a) throws EvaluationException {
        check("binPow", a);
        return binPow(10L, a);
    }

    private void checkLog10(final Long a) {
        if (a <= 0) {
            throw new IllegalLogValue("Positive log value expected");
        }
    }

    private int log(Long a, final Integer base) {
        int log = 0;
        while (a > 0) {
            log++;
            a /= base;
        }
        return (log == 0 ? 0 : log - 1);
    }

    @Override
    public Long log10(final Long a) throws EvaluationException {
        check("log10", a);
        return (long) log(a, 10);
    }

    @Override
    public Long parseNumber(String number) {
        return Long.parseLong(number);
    }

    @Override
    public Long valueOf(int number) {
        return (long) number;
    }
}
