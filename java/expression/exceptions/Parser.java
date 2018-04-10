package expression.exceptions;

import expression.operators.CommonExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    CommonExpression parse(String expression) throws ParsingException;
}
