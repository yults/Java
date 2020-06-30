"use strict";

const cnst = value => () => value;
const additionalConsts = {"pi": Math.PI, "e": Math.E};
for (let i in additionalConsts) {
    this[i] = cnst(additionalConsts[i]);
}

const variables = {'x': 0, 'y': 1, 'z': 2};
const variable = function(a) {
    return (...args) => args[variables[a]];
}

const abstAction = act => (...args) => (...val) => {
    let ans = [];
    for (let i of args) {
        ans.push(i(...val));
    }
    return act(...ans);
};

let negate = abstAction(a => -a);
let add = abstAction((a, b) => a + b);
let subtract = abstAction((a, b) => a - b);
let multiply = abstAction((a, b) => a * b);
let divide = abstAction((a, b) => a / b);
let sin = abstAction(a => Math.sin(a));
let cos = abstAction(a => Math.cos(a));
