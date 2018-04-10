package expression.generic;

import expression.exceptions.EvaluationException;
import expression.exceptions.ParsingException;
import expression.exceptions.UnsupportedModeException;
import expression.generic.operators.CommonExpression;

public class GenericTabulator implements Tabulator {

    private GenericOperationTable <?> setMode(final String mode) {
        switch (mode) {
            case "i":
                return new IntegerOperation(true);
            case "u":
                return new IntegerOperation(false);
            case "l":
                return new LongOperation(false);
            case "s":
                return new ShortOperation(false);
            case "d":
                return new DoubleOperation();
            case "bi":
                return new BigIntegerOperation();
            default:
                throw new UnsupportedModeException("Mode " + mode + " is not supported");
        }
    }

    private <T> Object[][][] generateTable(GenericOperationTable <T> operation, final String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Parser <T> parser = new ExpressionParser<>(operation);
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        CommonExpression<T> expr;

        try {
            expr = parser.parse(expression);
        } catch (ParsingException e) {
            System.out.println(e.getMessage());
            return res;
        }

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        T arg1 = operation.valueOf(x);
                        T arg2 = operation.valueOf(y);
                        T arg3 = operation.valueOf(z);
                        res[x - x1][y - y1][z - z1] = expr.evaluate(arg1, arg2, arg3);
                    } catch (EvaluationException e) {
                        res[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return res;
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return generateTable(setMode(mode), expression, x1, x2, y1, y2, z1, z2);
    }
}
