package expression.parser;

import base.Triple;
import expression.*;

public class ExpressionParser implements Parser{

    private int exprPointer = 0;
    private String expression;
    private int number;
    private char name;
    private Token curToken;

    private enum Token{
            MUL, DIV, PLUS,
            MINUS, CL_BR, OP_BR,
            NUM, VAR, END
    }

    private void skipWhitespaces() {
        while (exprPointer < expression.length() && Character.isWhitespace(expression.charAt(exprPointer))) {
            exprPointer++;
        }
    }

    private int getNumberValue() {
        int res = 0;
        while (exprPointer < expression.length() && Character.isDigit(expression.charAt(exprPointer))) {
            res = res * 10 + Character.digit(expression.charAt(exprPointer), 10);
            exprPointer++;
        }
        return res;
    }

    private Token getToken() {
        skipWhitespaces();

        if (exprPointer == expression.length()) {
            return Token.END;
        }
        char ch = expression.charAt(exprPointer++);
        switch (ch) {
            case '*':
                return Token.MUL;
            case '/':
                return Token.DIV;
            case '+':
                return Token.PLUS;
            case '-':
                return Token.MINUS;
            case '(':
                return Token.OP_BR;
            case ')':
                return Token.CL_BR;
            default:
                if (Character.isDigit(ch)) {
                    exprPointer--;
                    number = getNumberValue();
                    return Token.NUM;
                }
                name = Character.toLowerCase(ch);
                return Token.VAR;
        }
    }

    private CommonExpression exprHigh() {
        curToken = getToken();
        switch (curToken) {
            case NUM:
                curToken = getToken();
                return new Const(number);
            case VAR:
                curToken = getToken();
                return new Variable(Character.toString(name));
            case MINUS:
                return new UnaryMinus(exprHigh());
            case OP_BR:
                CommonExpression expr = exprLow();
                curToken = getToken();
                return expr;
            default:
                return new Const(1);
        }
    }

    private CommonExpression exprMid() {
        CommonExpression expr = exprHigh();
        while (true) {
            switch (curToken) {
                case MUL:
                    expr = new Multiply(expr, exprHigh());
                    break;
                case DIV:
                    expr = new Divide(expr, exprHigh());
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression exprLow() {
        CommonExpression expr = exprMid();
        while (true) {
            switch (curToken) {
                case PLUS:
                    expr = new Add(expr, exprMid());
                    break;
                case MINUS:
                    expr = new Subtract(expr, exprMid());
                    break;
                default:
                    return expr;
            }
        }

    }


    @Override
    public CommonExpression parse(String expression) {
        this.expression = expression;
        exprPointer = 0;
        return exprLow();
    }
}
