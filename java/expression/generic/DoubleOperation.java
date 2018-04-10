package expression.generic;

import expression.exceptions.EvaluationException;
import expression.exceptions.UnsupportedOperationArgumentsTypesException;

public class DoubleOperation implements GenericOperationTable <Double> {
    @Override
    public Double add(final Double a, final Double b) {
        return a + b;
    }

    @Override
    public Double subtract(final Double a, final Double b) {
        return a - b;
    }

    @Override
    public Double multiply(final Double a, final Double b) {
        return a * b;
    }

    @Override
    public Double divide(final Double a, final Double b){
        return a / b;
    }

    @Override
    public Double negate(final Double a){
        return -a;
    }

    @Override
    public Double and(Double a, Double b) throws EvaluationException {
        throw new UnsupportedOperationArgumentsTypesException("Undefined result for Double & Double operation");
    }

    @Override
    public Double or(Double a, Double b) throws EvaluationException {
        throw new UnsupportedOperationArgumentsTypesException("Undefined result for Double | Double operation");
    }

    @Override
    public Double xor(Double a, Double b) throws EvaluationException {
        throw new UnsupportedOperationArgumentsTypesException("Undefined result for Double ^ Double operation");
    }

    @Override
    public Double pow10(Double a) throws EvaluationException {
        throw new UnsupportedOperationArgumentsTypesException("Can't apply log10 operation for Double argument");
    }

    @Override
    public Double log10(Double a) throws EvaluationException {
        throw new UnsupportedOperationArgumentsTypesException("Can't apply pow10 operation for Double argument");
    }

    @Override
    public Double parseNumber(String number) {
        return new Double(number);
    }

    @Override
    public Double valueOf(int number) {
        return (double) number;
    }
}
