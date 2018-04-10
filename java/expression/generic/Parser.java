package expression.generic;

import expression.exceptions.ParsingException;
import expression.generic.operators.CommonExpression;

public interface Parser <T> {
    CommonExpression <T> parse(String expression) throws ParsingException;
}
