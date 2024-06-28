"use strict"
const cnst = a => () => a;
const cube = a => (...args) => Math.pow((a(...args)), 3);
const negate = a => (...args) => -a(...args);
const cbrt = a => (...args) => Math.cbrt(a(...args));
const pi = cnst(Math.PI);
const e = cnst(Math.E);
const binaryOperation = f => (a, b) => (...args) => f(a(...args), b(...args));
const add = binaryOperation((a, b) => a + b);
const subtract = binaryOperation((a, b) => a - b);
const divide = binaryOperation((a, b) => a / b);
const multiply = binaryOperation((a, b) => a * b);

const variables = {"x": 0, "y": 1, "z": 2};
const variable = (name) => (...args) => args[variables[name]];

const expression = add(subtract(
    multiply(
        variable("x"),
        variable("x")
    )
    , multiply(
        variable("2"),
        variable("x")
    )
), cnst(1));
// for (let i = 0; i < 10; i++) {
//     println(expression(i, 0, 0));
// }
