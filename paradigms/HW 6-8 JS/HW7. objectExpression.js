"use strict";

function AbstractAction(doAction, action, ...args) {
    this.doAction = doAction;
    this.action = action;
    this.args = args;
}

AbstractAction.prototype.toString = function() {
    return this.args.join(" ")  + " " + this.action;
};

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

function Const(value) {
    this.value = value;
}

Const.prototype.toString = function() {
    return this.value.toString();
};

Const.prototype.evaluate = function(x, y, z) {
    return this.value;
};

const variables = {"x": 0, "y": 1, "z": 2};

function Variable(sign) {
    this.sign = sign;
}

Variable.prototype.toString = function() {
    return this.sign;
};

Variable.prototype.evaluate = function (sign) {
    return arguments[variables[this.sign]];
};

