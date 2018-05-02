package expression.exceptions;

import expression.operators.CommonExpression;

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();

        try {
            CommonExpression x = parser.parse("1.0");
            System.out.println(x.evaluate(0, 0, 0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
