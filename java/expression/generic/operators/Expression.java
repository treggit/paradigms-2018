package expression.generic.operators;

import expression.exceptions.EvaluationException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression <T> {
    T evaluate(T x) throws EvaluationException;
}
