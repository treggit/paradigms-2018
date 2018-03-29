package expression;

import expression.exceptions.EvaluationException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TripleExpressionTest extends ExpressionTest {
    public static void main(final String[] args) {
        new TripleExpressionTest().run();
    }

    @Override
    protected void test() {
        super.test();

        testExpression("10", new Const(10), (x, y, z) -> 10);
        testExpression("x", new Variable("x"), (x, y, z) -> x);
        testExpression("y", new Variable("y"), (x, y, z) -> y);
        testExpression("z", new Variable("z"), (x, y, z) -> z);
        testExpression("x+2", new CheckedAdd(new Variable("x"), new Const(2)), (x, y, z) -> x + 2);
        testExpression("2-y", new CheckedSubtract(new Const(2), new Variable("y")), (x, y, z) -> 2 - y);
        testExpression("3*z", new CheckedMultiply(new Const(3), new Variable("z")), (x, y, z) -> 3 * z);
        testExpression("x/-2", new CheckedDivide(new Variable("x"), new Const(-2)), (x, y, z) -> -x / 2);
        testExpression(
                "x*y+(z-1)/10",
                new CheckedAdd(
                        new CheckedMultiply(new Variable("x"), new Variable("y")),
                        new CheckedDivide(new CheckedSubtract(new Variable("z"), new Const(1)), new Const(10))
                ),
                (x, y, z) -> x * y + (z - 1) / 10
        );
    }

    private void testExpression(final String description, final TripleExpression actual, final TripleExpression expected) {
        System.out.println("Testing " + description);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    assertEquals(String.format("f(%d, %d, %d)", i, j, k), actual.evaluate(i, j, k), expected.evaluate(i, j, k));
                }
            }
        }
    }
}
