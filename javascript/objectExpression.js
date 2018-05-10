'use strict';

function argToArray(obj) {
    var arr = [];
    //console.log(obj);
    for (var i = 0; i < obj.length; i++) {
        arr = arr.concat(obj[i]);
    }
    //console.log(arr);
    return arr;
}

function Expression(operation, id, operands) {
    this.operation = operation;
    this.operands = argToArray(operands);
    this.id = id;
}

Expression.prototype = {
    evaluate: function () {
        var args = arguments;
        var arr = this.operands.map(function(element) {
            return element.evaluate.apply(element, args);
        });
        if (arr.length === 0) {
            return this.operation.apply(null, arguments);
        }
        return this.operation.apply(null, arr);
    },

    toString: function() {
        return this.toStringImpl().trim();
    },

    toStringImpl: function() {
        return this.operands.map(function(element) {
            return element.toStringImpl.call(element);
        }).join("").concat(this.id, " ");
    },

    prefix: function() {
        return this.prefixImpl().trim();
    },

    prefixImpl: function () {
        if (this.operands.length === 0) {
            return this.id;
        }
        return "(".concat(this.id, " ", this.operands.map(function (element) {
            return element.prefix.call(element);
        }).join(" "), ") ");
    }
};

function createOperator(operation, id) {
    function Operator() {
        Expression.call(this, operation, id, arguments)
    }
    Operator.prototype = Object.create(Expression.prototype);
    return Operator;
}

var Add = createOperator(function (a, b) {
    return a + b;
}, "+");

var Subtract = createOperator(function (a, b) {
    return a - b;
}, "-");

var Multiply = createOperator(function (a, b) {
    return a * b;
}, "*");

var Divide = createOperator(function (a, b) {
    return a / b;
}, "/");

var Negate = createOperator(function (a) {
    return -a;
}, "negate");

var Square = createOperator(function (a) {
    return a * a;
}, "square");

var Sqrt = createOperator(function (a) {
    return Math.sqrt(Math.abs(a));
}, "sqrt");

var ArcTan = createOperator(function (a) {
    return Math.atan(a);
}, "atan");

var Exp = createOperator(function (a) {
    return Math.exp(a);
}, "exp");

var Const = function (value) {
    Expression.call(this, function() {return value}, value.toString(), {});
};

Const.prototype = Object.create(Expression.prototype);

var Variable = function (name) {
    function identifyValue() {
        switch (name) {
            case "x":
                return arguments[0];
            case "y":
                return arguments[1];
            case "z":
                return arguments[2];
        }
    }
    Expression.call(this, identifyValue, name, {});
};

Variable.prototype = Object.create(Expression.prototype);

function ParsingException(message) {
    this.message = message;
}

ParsingException.prototype = Error.prototype;

function createException(message) {
    function Exception(expr, pos) {
        var pointer = "";
        for (var i = 0; i < pos; i++) {
            pointer += '-';
        }
        pointer += '^';
        for (i = pos; i < expr.length - 1; i++) {
            pointer += '-';
        }
        ParsingException.call(this, message + (pos + 1).toString() + '\n' + expr + '\n' + pointer + '\n');
    }
    Exception.prototype = Object.create(ParsingException.prototype);
    return Exception;
}

var MissingOpeningBracketException = createException("Expected opening bracket in position ");
var MissingClosingBracketException = createException("Expected closing bracket in position ");
var MissingBracketException = createException("Expected bracket in position ");
var IllegalVariableNameException = createException("Unsupported variable name in position ");
var IllegalConstValueException = createException("Unsupported const value in position ");
var MissingOperatorException = createException("Expected operator in position ");
var WrongArgumentsNumber = createException("Unexpected number of arguments for operator in position ");
var UnexpectedExpressionsException = createException("Expected end of line, arguments found in position ");

var ptr;
var prevPtr;
var expr;

function isLegalName(name) {
    if (typeof name !== "string") {
        return false;
    }
    return (name === "x" || name === "y" || name === "z");
}

function isLegalOperator(op) {
    if (typeof op !== "string") {
        return false;
    }
    return (op === "+" || op === "-" || op === "*" || op === "/"
        || op === "negate" || op === "sqrt" || op === "square" || op === "atan" || op === "exp");
}

function getOperatorProp(operator) {
    switch (operator) {
        case "+":
            return {constr: Add, operandsNum: 2};
        case "-":
            return {constr: Subtract, operandsNum: 2};
        case "*":
            return {constr: Multiply, operandsNum: 2};
        case "/":
            return {constr: Divide, operandsNum: 2};
        case "negate":
            return {constr: Negate, operandsNum: 1};
        case "sqrt":
            return {constr: Sqrt, operandsNum: 1};
        case "square":
            return {constr: Square, operandsNum: 1};
        case "atan":
            return {constr: ArcTan, operandsNum: 1};
        case "exp":
            return {constr: Exp, operandsNum: 1};
    }
}

function isWhitespace(ch) {
    return (ch.trim().length === 0);
}

function skipWhitespaces() {
    while (ptr < expr.length && isWhitespace(expr.charAt(ptr))) {
        ptr++;
    }
}

function isLetter(symbol) {
    return (symbol >= "a" && symbol <= "z");
}

function isBracket(symbol) {
    return (symbol === ")" || symbol === "(");
}

function legalSymbol(symbol) {
    return (!isWhitespace(symbol) && !isBracket(symbol));
}

function buildToken(accepts) {
    var res = "";
    while (ptr < expr.length && accepts(expr.charAt(ptr))) {
        res = res.concat(expr[ptr++]);
    }
    if (ptr < expr.length && res.length === 0) {
        res = res.concat(expr.charAt(ptr++));
    }
    return res;
}

function getToken() {
    skipWhitespaces();
    prevPtr = ptr;
    return {value: buildToken(legalSymbol), position: prevPtr};
}

function isNumber(token) {
    if (typeof(token) !== "string") {
        return;
    }
    //console.log(token, +token);
    return (!isNaN(+token));
}

function checkNext(checker, exception) {
    var next = getToken();
    if (!checker(next.value)) {
        throw new exception(expr, next.position);
    }
}

function getOperands(number) {
    var operands = [];
    while (ptr < expr.length && number--) {
        var token = getToken();
        if (token.value === "(") {
            ptr = prevPtr;
            operands.push(getExpression());
            continue;
        }
        if (token.value === ")") {
            return [];
        }
        if (isNumber(token.value)) {
            operands.push(new Const(+token.value));
            continue;
        }
        if (isLegalName(token.value)) {
            operands.push(new Variable(token.value));
            continue;
        }
        if (isLegalOperator(token.value)) {
            throw new MissingBracketException(expr, token.position);
        }
        if (isLetter(token.value[0])) {
            throw new IllegalVariableNameException(expr, token.value);
        }
        throw new IllegalConstValueException(expr, token.position);
    }

    return operands;
}

function getExpression() {

    checkNext(function (symbol) {
        return symbol === "("
    }, MissingOpeningBracketException);

    var operator = getToken();
    if (!isLegalOperator(operator.value)) {
        throw new MissingOperatorException(expr, operator.position);
    }
    var prop = getOperatorProp(operator.value);
    var operands = getOperands(prop.operandsNum);
    var next = getToken();
    if (operands.length !== prop.operandsNum || (next.value !== ")" && next.value !== "")) {
        throw new WrongArgumentsNumber(expr, operator.position);
    }
    ptr = prevPtr;
    checkNext(function (symbol) {
        return symbol === ")"
    }, MissingClosingBracketException);
    return new prop.constr(operands);
}

function parse(str) {
    expr = str;
    ptr = prevPtr = 0;
    if (expr.length === 0) {
        throw new MissingBracketException(expr + " ", 0);
    }
    var token = getToken();
    if (token.value !== "(") {
        if (isNumber(token.value) && ptr === expr.length) {
            return new Const(+token.value);
        }
        if (isLegalName(token.value) && ptr === expr.length) {
            return new Variable(token.value);
        }
        throw new IllegalVariableNameException(expr, 0);
    }
    ptr = prevPtr;
    var res = getExpression();
    if (ptr !== expr.length) {
        skipWhitespaces();
        throw new UnexpectedExpressionsException(expr, ptr);
    }
    return res;
}

function parsePrefix(expr) {
    expr = expr.trim();
    return parse(expr);
}

//console.log(parsePrefix('(+ x (negate x)').evaluate(10));
