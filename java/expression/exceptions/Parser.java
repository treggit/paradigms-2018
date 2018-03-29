package expression.exceptions;

import expression.CommonExpression;
import expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    CommonExpression parse(String expression) throws ParsingException;
}
