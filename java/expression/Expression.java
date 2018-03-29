package expression;

import expression.exceptions.EvaluationException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression {
    int evaluate(int x) throws EvaluationException;
}
