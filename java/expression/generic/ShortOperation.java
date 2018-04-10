package expression.generic;

import expression.exceptions.*;

public class ShortOperation implements GenericOperationTable <Short> {

    private boolean checked;

    public ShortOperation(boolean checked) {
        this.checked = checked;
    }

    private void check(final String op, final Short a, final Short b) {
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

    private void check(final String op, final Short a) {
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

    private void checkAdd(final Short a, final Short b) {
        if ((b < 0 && Short.MIN_VALUE - b > a) || (b > 0 && Short.MAX_VALUE - b < a)) {
            throw new OverflowException("add");
        }
    }

    @Override
    public Short add(final Short a, final Short b) throws EvaluationException {
        check("add", a, b);
        return (short) (a + b);
    }


    private void checkSubtract(final Short a, final Short b) {
        if ((b < 0 && Short.MAX_VALUE + b < a) || (b > 0 && Short.MIN_VALUE + b > a)) {
            throw new OverflowException("subtract");
        }
    }

    @Override
    public Short subtract(final Short a, final Short b) throws EvaluationException {
        check("sub", a, b);
        return (short) (a - b);
    }


    private void checkMultiply(final Short a, final Short b) {
        if (a > 0 && b > 0 && Short.MAX_VALUE / b < a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b > 0 && Short.MIN_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a < 0 && b < 0 && Short.MAX_VALUE / b > a) {
            throw new OverflowException("multiply");
        }
        if (a > 0 && b < 0 && Short.MIN_VALUE / a > b) {
            throw new OverflowException("multiply");
        }
    }

    @Override
    public Short multiply(final Short a, final Short b) throws EvaluationException {
        check("mul", a, b);
        return (short) (a * b);
    }

    private void checkDivide(final Short a, final Short b) {

        if (a == Short.MIN_VALUE && b == -1) {
            throw new OverflowException("divide");
        }
    }


    @Override
    public Short divide(final Short a, final Short b) throws EvaluationException {
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        check("div", a, b);
        return (short) (a / b);
    }

    private void checkNegation(final Short a) {
        if (a == Short.MIN_VALUE) {
            throw new OverflowException("negation");
        }
    }

    @Override
    public Short negate(final Short a) throws EvaluationException {
        check("neg", a);
        return (short) -a;
    }

    @Override
    public Short and(final Short a, final Short b) throws EvaluationException {
        return (short) (a & b);
    }

    @Override
    public Short or(final Short a, final Short b) throws EvaluationException {
        return (short) (a | b);
    }

    @Override
    public Short xor(final Short a, final Short b) throws EvaluationException {
        return (short) (a ^ b);
    }

    void checkBinPow(final Short a) {
        if (a < 0) {
            throw new IllegalDegreeValue("Positive degree value expected");
        }
    }

    private short binPow(short x, short deg) {
        short res = 1;
        while (deg > 0) {
            if (deg % 2 == 1) {
                check("mul", res, x);
                res *= x;
            }
            deg = (short) (deg >> 1);
            if (deg > 0) {
                check("mul", x, x);
                x *= x;
            }
        }
        return res;
    }

    @Override
    public Short pow10(final Short a) throws EvaluationException {
        check("binPow", a);
        return binPow((short) 10, a);
    }

    private void checkLog10(final Short a) {
        if (a <= 0) {
            throw new IllegalLogValue("Positive log value expected");
        }
    }

    private Short log(Short a, final Short base) {
        short log = 0;
        while (a > 0) {
            log++;
            a = (short) (a / base);
        }
        return (log == (short) 0 ? (short) 0 : (short) (log - 1));
    }

    @Override
    public Short log10(final Short a) throws EvaluationException {
        check("log10", a);
        return log(a, (short) 10);
    }

    @Override
    public Short parseNumber(String number) {
        return Short.parseShort(number);
    }

    @Override
    public Short valueOf(int number) {
        return (short) number;
    }
}
