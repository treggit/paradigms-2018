package expression.generic;

import expression.generic.operators.*;
import expression.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressionParser<T> implements Parser<T> {

    private int exprPointer = 0;
    private int prevPointer = 0;
    private String expression;
    private T number;
    private String name;
    private Token curToken;
    private boolean isNeg = false;
    private boolean isNegConst = false;
    private List<Integer> unclosedBrackets;
    private final GenericOperationTable<T> operation;

    public ExpressionParser(GenericOperationTable<T> operation) {
        legalNames.add("x");
        legalNames.add("y");
        legalNames.add("z");
        this.operation = operation;
    }

    private void init() {
        exprPointer = prevPointer = 0;
        isNegConst = isNeg = false;
        unclosedBrackets = new ArrayList<>();
    }

    static private Set<String> legalNames = new HashSet<>();

    private enum Token {
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

    private void digitConstructor(StringBuilder sb) {
        while (exprPointer < expression.length() && Character.isDigit(expression.charAt(exprPointer))) {
            sb.append(expression.charAt(exprPointer));
            exprPointer++;
        }
    }

    private void doubleConstructor(StringBuilder sb) {
        if (exprPointer < expression.length() && sb.length() > 0 && Character.toLowerCase(expression.charAt(exprPointer)) == 'e'
                && (expression.charAt(exprPointer) == '-' || expression.charAt(exprPointer) == '+')) {
            sb.append(expression.charAt(exprPointer++));
        }
        digitConstructor(sb);
    }

    private T getNumberValue() {
        StringBuilder sb = new StringBuilder();
        if (isNeg) {
            isNegConst = true;
            sb.append('-');
        }
        digitConstructor(sb);
        if (exprPointer < expression.length() && (expression.charAt(exprPointer) == '.'
                || expression.charAt(exprPointer) == ',' || Character.toLowerCase(expression.charAt(exprPointer)) == 'e')) {
            sb.append(expression.charAt(exprPointer++));
            doubleConstructor(sb);
        }
        try {
            return operation.parseNumber(sb.toString());
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
                        if (curToken == Token.NUM && number.equals(operation.valueOf(10))) {
                            return Token.LOG10;
                        }
                        exprPointer = prevPointer;
                    }
                    if (name.equals("pow")) {
                        curToken = getToken();
                        if (curToken == Token.NUM && number.equals(operation.valueOf(10))) {
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

    private CommonExpression<T> exprHigh() {
        curToken = getToken();
        isNeg = false;
        CommonExpression<T> expr;
        switch (curToken) {
            case NUM:
                curToken = getToken();
                checkOperator();
                return new Const<>(number);
            case VAR:
                if (!legalNames.contains(name)) {
                    throw new UnsupportedVariableName(expression, prevPointer);
                }
                curToken = getToken();
                checkOperator();
                return new Variable<>(name);
            case MINUS:
                isNeg = true;
                isNegConst = false;
                expr = exprHigh();
                if (isNegConst) {
                    isNegConst = false;
                    return expr;
                }
                return new CheckedNegate<>(expr, operation);
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
                return new Log10<>(expr, operation);
            case POW10:
                expr = exprHigh();
                return new Pow10<>(expr, operation);
            default:
                throw new MissingPrimaryException(expression, prevPointer); //primary expected
        }
    }

    private CommonExpression<T> exprMid() {
        CommonExpression<T> expr = exprHigh();
        while (true) {
            switch (curToken) {
                case MUL:
                    expr = new CheckedMultiply<>(expr, exprHigh(), operation);
                    break;
                case DIV:
                    expr = new CheckedDivide<>(expr, exprHigh(), operation);
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression<T> exprLow() {
        CommonExpression<T> expr = exprMid();
        while (true) {
            switch (curToken) {
                case PLUS:
                    expr = new CheckedAdd<>(expr, exprMid(), operation);
                    break;
                case MINUS:
                    CommonExpression<T> e = exprMid();
                    expr = new CheckedSubtract<>(expr, e, operation);
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression<T> exprAnd() {
        CommonExpression<T> expr = exprLow();

        while (true) {
            switch (curToken) {
                case AND:
                    expr = new CheckedAnd<>(expr, exprLow(), operation);
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression<T> exprXor() {
        CommonExpression<T> expr = exprAnd();

        while (true) {
            switch (curToken) {
                case AND:
                    expr = new CheckedXor<>(expr, exprAnd(), operation);
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression<T> exprOr() {
        CommonExpression<T> expr = exprXor();

        while (true) {
            switch (curToken) {
                case AND:
                    expr = new CheckedOr<>(expr, exprXor(), operation);
                    break;
                default:
                    return expr;
            }
        }
    }

    private CommonExpression<T> expr() {
        return exprOr();
    }

    @Override
    public CommonExpression<T> parse(String expression) throws ParsingException {
        this.expression = expression;
        init();
        CommonExpression<T> res = expr();
        if (curToken == Token.CL_BR && unclosedBrackets.isEmpty()) {
            throw new MissingOpeningBracketException(expression, prevPointer);
        }
        if (exprPointer != expression.length()) {
            throw new MissingOperatorException(expression, prevPointer);
        }

        return res;
    }
}
