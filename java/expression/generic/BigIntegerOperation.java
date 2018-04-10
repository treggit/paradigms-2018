package expression.generic;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.EvaluationException;
import expression.exceptions.UnsupportedOperationArgumentsTypesException;

import java.math.BigInteger;

public class BigIntegerOperation implements GenericOperationTable <BigInteger> {
    @Override
    public BigInteger add(final BigInteger a, final BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger subtract(final BigInteger a, final BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger multiply(final BigInteger a, final BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger divide(final BigInteger a, final BigInteger b) throws EvaluationException {
        if (b.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException();
        }
        return a.divide(b);
    }

    @Override
    public BigInteger negate(final BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger and(final BigInteger a, final BigInteger b) {
        return a.and(b);
    }

    @Override
    public BigInteger or(final BigInteger a, final BigInteger b) {
        return a.or(b);
    }

    @Override
    public BigInteger xor(final BigInteger a, final BigInteger b) {
        return a.xor(b);
    }

    @Override
    public BigInteger log10(final BigInteger a) throws EvaluationException {
        throw new UnsupportedOperationArgumentsTypesException("Can't apply log10 operation for BigInteger argument");
    }

    @Override
    public BigInteger pow10(final BigInteger a) throws EvaluationException {
        throw new UnsupportedOperationArgumentsTypesException("Can't apply pow10 operation for BigInteger argument");
    }

    @Override
    public BigInteger parseNumber(final String number) {
        return new BigInteger(number);
    }

    @Override
    public BigInteger valueOf(int number) {
        return BigInteger.valueOf(number);
    }
}
