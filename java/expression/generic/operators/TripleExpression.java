package expression.generic.operators;

import expression.exceptions.EvaluationException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression <T> {
    T evaluate(T x, T y, T z) throws EvaluationException;
}
