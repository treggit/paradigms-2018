package expression;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Input value expected");
            return;
        }

        int x = Integer.parseInt(args[0]);
        System.out.println(new Multiply(
                new Variable("x"),
                new Variable("x")
        ).evaluate(10));

        Expression expression = new Add(
                                    new Subtract(
                                            new Multiply(
                                                    new Variable("x"),
                                                    new Variable("x")
                                            ),
                                            new Multiply(
                                                    new Const(2),
                                                    new Variable("x")
                                            )
                                    ),
                                    new Const(1)
                            );

        System.out.println(expression.evaluate(x));
    }
}
