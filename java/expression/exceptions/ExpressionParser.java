package expression.exceptions;

import expression.*;

import java.util.HashSet;
import java.util.Set;

public class ExpressionParser implements Parser{

    private int exprPointer = 0;
    private int prevPointer = 0;
    private String expression;
    private int number;
    private String name;
    private Token curToken;
    private boolean isNeg = false;
    private boolean isNegConst = false;
    private int unclosedBrackets = 0;

    void init() {
        exprPointer = prevPointer = unclosedBrackets = 0;
        isNegConst = isNeg = false;
    }

    static private Set <String> legalNames = new HashSet<>();

    static {
        legalNames.add("x");
        legalNames.add("y");
        legalNames.add("z");
    }

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

    private int getNumberValue() throws ParsingException {
        int res = 0;
        while (exprPointer < expression.length() && Character.isDigit(expression.charAt(exprPointer))) {
            int dig = Character.digit(expression.charAt(exprPointer), 10);
            if (isNeg) {
                dig = -dig;
            }
            try {
                res = Math.addExact(Math.multiplyExact(res, 10), dig);
            } catch (ArithmeticException e) {
                throw new IllegalConstException(expression, prevPointer);
            }
            exprPointer++;
        }
        if (isNeg) {
            isNegConst = true;
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

    private Token getToken() throws ParsingException{
        skipWhitespaces();
        prevPointer = exprPointer;
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
                if (Character.isLetter(ch)) {
                    name = getName();
                    return Token.VAR;
                }
                throw new UnsupportedOperation(expression, prevPointer);
        }
    }

    private void checkOperator() throws ParsingException {
        if (curToken == Token.OP_BR) {
            throw new MissingOperatorException(expression, prevPointer);
        }
        if (curToken == Token.CL_BR && unclosedBrackets == 0) {
            throw new MissingOpeningBracketException(expression, prevPointer);
        }
        if (curToken == Token.VAR || curToken == Token.NUM) {
            throw new MissingOperatorException(expression, prevPointer);
        }
    }

    private CommonExpression exprHigh() throws ParsingException {
        curToken = getToken();
        isNeg = false;
        CommonExpression expr;
        switch (curToken) {
            case NUM:
                curToken = getToken();
                checkOperator();
                return new Const(number);
            case VAR:
                curToken = getToken();
                checkOperator();
                if (!legalNames.contains(name)) {
                    throw new UnsupportedVariableName(expression, prevPointer);
                }
                return new Variable(name);
            case MINUS:
                isNeg = true;
                isNegConst = false;
                expr = exprHigh();
                if (isNegConst) {
                    isNegConst = false;
                    return expr;
                }
                return new CheckedNegate(expr);
            case OP_BR:
                unclosedBrackets++;
                expr = expr();
                if (curToken != Token.CL_BR) {
                    throw new MissingClosingBracketException(expression, prevPointer);
                }
                unclosedBrackets--;
                curToken = getToken();
                return expr;
            default:
                throw new MissingPrimaryException(expression, prevPointer); //primary expected
        }
    }

    private CommonExpression exprMid() throws ParsingException {
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

    private CommonExpression exprLow() throws ParsingException {
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

    private CommonExpression exprAnd() throws ParsingException {
        CommonExpression expr = exprLow();

        while (true) {
            switch (curToken) {
                case AND:
                    expr = new CheckedAnd(expr, exprLow());
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression exprXor() throws ParsingException {
        CommonExpression expr = exprAnd();

        while (true) {
            switch (curToken) {
                case AND:
                    expr = new CheckedXor(expr, exprAnd());
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression exprOr() throws ParsingException {
        CommonExpression expr = exprXor();

        while (true) {
            switch (curToken) {
                case AND:
                    expr = new CheckedOr(expr, exprXor());
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression expr() throws ParsingException {
        return exprOr();
    }

    @Override
    public CommonExpression parse(String expression) throws ParsingException {
        this.expression = expression;
        init();
        CommonExpression res = expr();
        if (curToken == Token.CL_BR && unclosedBrackets == 0) {
            throw new MissingOpeningBracketException(expression, prevPointer);
        }
        if (exprPointer != expression.length()) {
            throw new MissingOperatorException(expression, prevPointer);
        }

        return res;
    }
}
