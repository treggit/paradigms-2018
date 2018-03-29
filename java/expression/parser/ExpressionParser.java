package expression.parser;

import expression.*;

public class ExpressionParser implements Parser{

    private int exprPointer = 0;
    private String expression;
    private int number;
    private String name;
    private Token curToken;

    private enum Token{
            MUL, DIV, PLUS,
            MINUS, CL_BR, OP_BR,
            NUM, VAR, END,
            XOR, AND, OR
    }

    private void skipWhitespaces() {
        while (exprPointer < expression.length() && Character.isWhitespace(expression.charAt(exprPointer))) {
            exprPointer++;
        }
    }

    private int getNumberValue() {
        int res = 0;
        while (exprPointer < expression.length() && Character.isDigit(expression.charAt(exprPointer))) {
            int dig = Character.digit(expression.charAt(exprPointer), 10);
            res = res * 10 + dig;
            exprPointer++;
        }
        return res;
    }

    private String getName() {
        StringBuilder sb = new StringBuilder();
        while (exprPointer < expression.length() && Character.isLetter(expression.charAt(exprPointer))) {
            sb.append(expression.charAt(exprPointer));
            exprPointer++;
        }
        return sb.toString();
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
            case '|':
                return Token.OR;
            case '&':
                return Token.AND;
            case '^':
                return Token.XOR;
            default:
                exprPointer--;
                if (Character.isDigit(ch)) {
                    number = getNumberValue();
                    return Token.NUM;
                }
                name = getName();
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
                return new Variable(name);
            case MINUS:
                return new CheckedNegate(exprHigh());
            case OP_BR:
                CommonExpression expr = expr();
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
                    expr = new CheckedMultiply(expr, exprHigh());
                    break;
                case DIV:
                    expr = new CheckedDivide(expr, exprHigh());
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
                    expr = new CheckedAdd(expr, exprMid());
                    break;
                case MINUS:
                    CommonExpression e = exprMid();
                    expr = new CheckedSubtract(expr, e);
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression exprAnd() {
        CommonExpression expr = exprLow();

        while (true) {
            if (curToken == Token.AND) {
                expr = new CheckedAnd(expr, exprLow());
            } else {
                return expr;
            }
        }
    }

    private CommonExpression exprXor() {
        CommonExpression expr = exprAnd();

        while (true) {
            if (curToken == Token.XOR) {
                expr = new CheckedXor(expr, exprAnd());
            } else {
                return expr;
            }
        }
    }

    private CommonExpression exprOr() {
        CommonExpression expr = exprXor();

        while (true) {
            if (curToken == Token.OR) {
                expr = new CheckedOr(expr, exprXor());
            } else {
                return expr;
            }
        }
    }

    private CommonExpression expr() {
        return exprOr();
    }


    @Override
    public CommonExpression parse(String expression) {
        this.expression = expression;
        exprPointer = 0;
        return expr();
    }
}
