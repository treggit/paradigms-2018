package expression.exceptions;

import expression.operators.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<Integer> unclosedBrackets;

    private void init() {
        exprPointer = 0;
        prevPointer = 0;
        isNegConst = isNeg = false;
        unclosedBrackets = new ArrayList<>();
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
        XOR, AND, OR,
        LOG10, POW10
    }

    private void skipWhitespaces() {
        while (exprPointer < expression.length() && Character.isWhitespace(expression.charAt(exprPointer))) {
            exprPointer++;
        }
    }

    private int getNumberValue()  {
        StringBuilder sb = new StringBuilder();
        if (isNeg) {
            isNegConst = true;
            sb.append('-');
        }
        while (exprPointer < expression.length() && Character.isDigit(expression.charAt(exprPointer))) {
            sb.append(expression.charAt(exprPointer));
            exprPointer++;
        }
        try {
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw new IllegalConstException(expression, prevPointer);
        }
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
                    isNeg = false;
                    name = getName();
                    if (name.equals("log")) {
                        curToken = getToken();
                        if (curToken == Token.NUM && number == 10) {
                            return Token.LOG10;
                        }
                        exprPointer = prevPointer;
                    }
                    if (name.equals("pow")) {
                        curToken = getToken();
                        if (curToken == Token.NUM && number == 10) {
                            return Token.POW10;
                        }
                        exprPointer = prevPointer;
                    }
                    return Token.VAR;
                }
                throw new UnsupportedOperation(expression, prevPointer);
        }
    }

    private void checkOperator() {
        if (curToken == Token.OP_BR) {
            throw new MissingOperatorException(expression, prevPointer);
        }
        if (curToken == Token.CL_BR && unclosedBrackets.isEmpty()) {
            throw new MissingOpeningBracketException(expression, prevPointer);
        }
        if (curToken == Token.VAR || curToken == Token.NUM) {
            throw new MissingOperatorException(expression, prevPointer);
        }
    }

    private CommonExpression exprHigh() {
        curToken = getToken();
        isNeg = false;
        CommonExpression expr;
        switch (curToken) {
            case NUM:
                curToken = getToken();
                checkOperator();
                return new Const(number);
            case VAR:
                if (!legalNames.contains(name)) {
                    throw new UnsupportedVariableName(expression, prevPointer);
                }
                curToken = getToken();
                checkOperator();
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
                unclosedBrackets.add(prevPointer);
                expr = expr();
                if (curToken != Token.CL_BR) {
                    throw new MissingClosingBracketException(expression, unclosedBrackets.get(unclosedBrackets.size() - 1));
                }
                unclosedBrackets.remove(unclosedBrackets.size() - 1);
                curToken = getToken();
                return expr;
            case LOG10:
                expr = exprHigh();
                return new Log10(expr);
            case POW10:
                expr = exprHigh();
                return new Pow10(expr);
            default:
                throw new MissingPrimaryException(expression, prevPointer); //primary expected
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
            switch (curToken) {
                case AND:
                    expr = new CheckedAnd(expr, exprLow());
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression exprXor() {
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

    private CommonExpression exprOr() {
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

    private CommonExpression expr()  {
        return exprOr();
    }

    @Override
    public CommonExpression parse(String expression) throws ParsingException {
        this.expression = expression;
        init();
        CommonExpression res = expr();
        if (curToken == Token.CL_BR && unclosedBrackets.isEmpty()) {
            throw new MissingOpeningBracketException(expression, prevPointer);
        }
        if (exprPointer != expression.length()) {
            throw new MissingOperatorException(expression, prevPointer);
        }

        return res;
    }
}
