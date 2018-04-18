"use strict";

var operator = function (f) {
    return function () {
        var operands = arguments;
        return function () {
            var value = arguments;
            var arr = [];
            for (var i = 0; i < operands.length; i++) {
                arr.push(operands[i].apply(null, value));
            }
            return f.apply(null, arr);
        }
    }
};

var add = operator(function (a, b) {
    return a + b;
});

var subtract = operator(function (a, b) {
    return a - b;
});

var multiply = operator(function (a, b) {
    return a * b;
});

var divide = operator(function (a, b) {
    return a / b;
});

var negate = operator(function (a) {
    return -a;
});

var cnst = function (a) {
    return function () {
        return a;
    }
};

var variable = function (name) {
    return function () {
        switch (name) {
            case "x":
                return arguments[0];
        }
    };
};
