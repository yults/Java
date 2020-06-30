"use strict";

function AbstractAction(doAction, action, ...args) {
    this.doAction = doAction;
    this.action = action;
    this.args = args;
}

AbstractAction.prototype.toString = function () {
    return this.args.join(" ")  + " " + this.action;
};

AbstractAction.prototype.prefix = function () {
    return '(' + this.action + " " + this.args.map(arg => arg.prefix()).join(" ") + ')';
}

AbstractAction.prototype.evaluate = function(x, y, z) {
    let evlArgs = this.args.map(arg => arg.evaluate(x, y, z));
    return this.doAction(...evlArgs);
};

Negate.prototype = Object.create(AbstractAction.prototype);
function Negate(right) {
    AbstractAction.call(this, a => -a, "negate", right);
}

Sinh.prototype = Object.create(AbstractAction.prototype);
function Sinh(right) {
    AbstractAction.call(this, a => Math.sinh(a), "sinh", right);
}

Cosh.prototype = Object.create(AbstractAction.prototype);
function Cosh(right) {
    AbstractAction.call(this, a => Math.cosh(a), "cosh", right);
}

Add.prototype = Object.create(AbstractAction.prototype);
function Add(left, right) {
    AbstractAction.call(this, (a, b) => a + b, "+", left, right);
}

Subtract.prototype = Object.create(AbstractAction.prototype);
function Subtract(left, right) {
    AbstractAction.call(this, (a, b) => a - b, "-", left, right);
}

Multiply.prototype = Object.create(AbstractAction.prototype);
function Multiply(left, right) {
    AbstractAction.call(this, (a, b) => a * b, "*", left, right);
}

Divide.prototype = Object.create(AbstractAction.prototype);
function Divide(left, right) {
    AbstractAction.call(this, (a, b) => a / b, "/", left, right);
}

Sum.prototype = Object.create(AbstractAction.prototype);
function Sum(...args) {
    AbstractAction.call(this,  (...args) => {
        if (args.length == 0) return 0;
       return args.reduce((ans, operand) => ans + operand);
    }, "sum", ...args);
}

Avg.prototype = Object.create(AbstractAction.prototype);
function Avg(...args) {
    AbstractAction.call(this,(...args) => {
        if (args.length == 0) return 0;
        return args.reduce((ans, operand) => ans + operand) / args.length;
    }, "avg", ...args);
}

function Const(value) {
    this.value = value;
}

Const.prototype.toString = function () {
    return this.value.toString();
};

Const.prototype.evaluate = function () {
    return this.value;
};

Const.prototype.prefix = function () {
    return this.value.toString();
};

function Variable(sign) {
    this.sign = sign;
}

Variable.prototype.toString = function () {
    return this.sign;
};

Variable.prototype.evaluate = function (sign) {
    return arguments[Variables[this.sign]];
};

Variable.prototype.prefix = function () {
    return this.sign;
};

Exception.prototype = Error.prototype;
function Exception(message) {
    this.message = message;
}

BracketException.prototype = Exception.prototype;
function BracketException(s) {
    Exception.call(this, "BracketException" + s);
}

ActionExceptedException.prototype = Exception.prototype;
function ActionExceptedException() {
    Exception.call(this, "ActionExceptedException");
}

EmptyFileException.prototype = Exception.prototype;
function EmptyFileException() {
    Exception.call(this, "EmptyFileException");
}

UnknownSymbolException.prototype = Exception.prototype;
function UnknownSymbolException(s, x) {
    Exception.call(this, "Unknown " + s + x);
}

ParsingException.prototype = Exception.prototype;
function ParsingException(s) {
    Exception.call(this, "ParsingException" + s);
}

VariableOpException.prototype = Exception.prototype;
function VariableOpException() {
    Exception.call(this, "VariableOpException");
}

ConstOpException.prototype = Exception.prototype;
function ConstOpException() {
    Exception.call(this, "ConstOpException");
}

WrongNumberOfArgs.prototype = Exception.prototype;
function WrongNumberOfArgs(x, y) {
    Exception.call(this, "WrongNumberOfArgs: " + x + " args, expected " + y);
}

const Tokens = {
    "+": Add,
    "-": Subtract,
    "/": Divide,
    "*": Multiply,
    "negate": Negate,
    "sum": Sum,
    "avg": Avg
};

const Arity = {
    "+": 2,
    "-": 2,
    "/": 2,
    "*": 2,
    "negate": 1,
};

const Variables = {"x": 0, "y": 1, "z": 2};

let parsingStream;
let indexStream;
let currentToken;
let brackets;

function parsePrefix(expr) {
    parsingStream = expr;
    indexStream = 0;
    brackets = 0;
    if (parsingStream.length === 0) {
        throw new EmptyFileException();
    }
    if (parsingStream.length > 0) {
        let res = base();
        skipWhiteSpace();
        if (indexStream < parsingStream.length) {
            if (currentToken in Variables) {
                throw new VariableOpException();
            }
            else if (isConst(currentToken)) {
                throw new ConstOpException();
            }
            else {
                throw new UnknownSymbolException(" : unknown op or excessive inf", "");
            }
        }
        if (brackets === 0) {
            return res;
        }
        else {
            throw new BracketException();
        }
    }
    else {
        throw new ParsingException();
    }
}

function base() {
    getNextToken();
    if (currentToken === "(") {
        getNextToken();
        if (currentToken === ")") {
            throw new BracketException(" : empty op");
        }
        if (currentToken in Tokens) {
            return getAction();
        }
    } else if (currentToken in Variables) {
        return new Variable(currentToken);
    } else if (isConst(currentToken)) {
        return new Const(parseInt(currentToken));
    } else if (currentToken === ")") {
        return currentToken;
    } else {
        throw new UnknownSymbolException(" : unknown symbol or invalid number in pos - ", indexStream);
    }

}

function getNextToken() {
    skipWhiteSpace();
    let nextToken;
    if (parsingStream[indexStream] === "(" || parsingStream[indexStream] === ")") {
        if (parsingStream[indexStream] === "(") {
            brackets++;
        } else {
            brackets--;
        }
        nextToken = parsingStream[indexStream];
        indexStream++;
    } else {
        let indexBegin = indexStream;
        while (!'()'.includes(parsingStream[indexStream]) && !/\s/.test(parsingStream[indexStream]) && indexStream < parsingStream.length) {
            indexStream++;
        }
        nextToken = parsingStream.substring(indexBegin, indexStream);
    }
    currentToken = nextToken;
};

function getAction() {
    let stack = [];
    let act = currentToken;
    let next = base();
    while (next !== ")" && indexStream < parsingStream.length) {
        stack.push(next);
        next = base();
    }
    if (Arity[act] !== stack.length && act !== "avg" && act !== "sum") {
        throw new WrongNumberOfArgs(stack.length, Arity[act]);
    }
    return new Tokens[act](...stack);
}

function isConst() {
    let ind = 0;
    if (currentToken.length === 1 && !isDigit(currentToken[ind])) {
        return false;
    }
    if (currentToken[0] === "-") {
        ind++;
    }
    while (isDigit(currentToken[ind]) && ind < currentToken.length) {
        ind++;
    }
    return ind === currentToken.length;
}

function isDigit(char) {
    return (char >= '0' && char <= '9');
}

function skipWhiteSpace() {
    while (indexStream < parsingStream.length && /\s/.test(parsingStream[indexStream])) {
        indexStream++;
    }
}




