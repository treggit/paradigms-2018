package expression.operators;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ExpressionTest extends BaseTest {
    public static void main(final String[] args) {
        new ExpressionTest().run();
    }

    @Override
    protected void test() {
        testExpression("10", new Const(10), x -> 10);
        testExpression("x", new Variable("x"), x -> x);
        testExpression("x+2", new CheckedAdd(new Variable("x"), new Const(2)), x -> x + 2);
        testExpression("2-x", new CheckedSubtract(new Const(2), new Variable("x")), x -> 2 - x);
        testExpression("3*x", new CheckedMultiply(new Const(3), new Variable("x")), x -> 3*x);
        testExpression("x/-2", new CheckedDivide(new Variable("x"), new Const(-2)), x -> -x / 2);
        testExpression(
                "x*x+(x-1)/10",
                new CheckedAdd(
                        new CheckedMultiply(new Variable("x"), new Variable("x")),
                        new CheckedDivide(new CheckedSubtract(new Variable("x"), new Const(1)), new Const(10))
                ),
                x -> x * x + (x - 1) / 10
        );
        testExpression("x*-1_000_000_000", new CheckedMultiply(new Variable("x"), new Const(-1_000_000_000)), x -> x * -1_000_000_000);
        testExpression("10/x", new CheckedDivide(new Const(10), new Variable("x")), x -> 10 / x);
        testExpression("x/x", new CheckedDivide(new Variable("x"), new Variable("x")), x -> x / x);
    }

    private void testExpression(final String description, final Expression actual, final Expression expected) {
        System.out.println("Testing " + description);
        for (int i = 0; i < 10; i++) {
            counter.nextTest();
            assertEquals(String.format("f(%d)", i), evaluate(actual, i), evaluate(expected, i));
            counter.passed();
        }
    }

    private Integer evaluate(final Expression expression, final int x) {
        try {
            return expression.evaluate(x);
        } catch (final ArithmeticException e) {
            return null;
        }
    }
}
