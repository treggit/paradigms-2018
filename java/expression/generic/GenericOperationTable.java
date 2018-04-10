package expression.generic;

public interface GenericOperationTable <T> {

    T add(final T a, final T b);
    T subtract(final T a, final T b);
    T multiply(final T a, final T b);
    T divide(final T a, final T b);
    T negate(final T a);
    T and(final T a, final T b);
    T or(final T a, final T b);
    T xor(final T a, final T b);
    T pow10(final T a);
    T log10(final T a);

    T parseNumber(final String number);
    T valueOf(final int number);
}
