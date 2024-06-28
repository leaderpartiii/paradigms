function Expression(expression, toString, evaluate, prefix) {
    expression.prototype.evaluate = evaluate;
    expression.prototype.toString = toString;
    expression.prototype.prefix = prefix;
    return expression;
}

const abstractOperation = Expression(function (name, operation, ...argc) {
    this.name = name;
    this.operation = operation;
    this.argc = argc;
    this.count = argc.length;
}, function () {
    let result = "";
    for (const argument of this.argc) {
        result += argument + " ";
    }
    return result + this.name;
}, function (...vars) {
    return this.operation(...this.argc.map(arg => arg.evaluate(...vars)));
}, function () {
    let result = "(" + this.name;
    for (const argument of this.argc) {
        result += " " + argument.prefix();
    }
    if (this.argc.length === 0) {
        result += " ";
    }
    return result + ")";
})

const mapOperation = {}

function AbstractOperation(operation, name) {
    const operationCall = function (...argc) {
        abstractOperation.call(this, name, operation, ...argc)
    };
    operationCall.prototype = Object.create(abstractOperation.prototype);
    operationCall.countOf = operation.length;
    mapOperation[name] = operationCall;
    return operationCall;
}

const Add = new AbstractOperation((x, y) => x + y, "+");
const Subtract = new AbstractOperation((x, y) => x - y, "-");
const Divide = new AbstractOperation((x, y) => x / y, "/");
const Multiply = new AbstractOperation((x, y) => x * y, "*");
const Negate = new AbstractOperation((x) => -x, "negate");
const Cosh = new AbstractOperation((x) => Math.cosh(x), "cosh");
const Sinh = new AbstractOperation((x) => Math.sinh(x), "sinh");
const Product = new AbstractOperation((...argc) => argc.reduce((acc, num) => acc * num, 1), "product");
const Geom = new AbstractOperation((...argc) => argc.reduce((acc, num) => (acc * Math.pow(Math.abs(num), 1 / argc.length)), 1), "geom");

const variables = {"x": 0, "y": 1, "z": 2};

function Variable(a) {
    this.a = a;
}

Expression(Variable, function () {
    return String(this.a)
}, function (...argc) {
    return parseFloat(argc[variables[this.a]])
}, function () {
    return String(this.a);
})

function Const(a) {
    this.a = a;
}

Expression(Const, function () {
    return String(this.a)
}, function () {
    return parseFloat(this.a)
}, function () {
    return String(this.a);
})

function isVarOrNum(arg) {
    return variables[arg] !== undefined || !isNaN(parseInt(arg));
}

const mapBrackets = {"(": 1, ")": -1};


const Exception = function (messageException) {
    this.messageException = messageException;
}
Exception.prototype = Object.create(Error.prototype);

function parse(str) {
    let array = [];
    let argc = str.trim().split(/\s+/);
    for (let argument of argc) {
        if (isVarOrNum(argument)) {
            if (!isNaN(parseInt(argument))) {
                array.push(new Const(parseInt(argument)));
            } else {
                array.push(new Variable(argument));
            }
        } else {
            if (mapOperation[argument].countOf === 1) {
                array.push(new mapOperation[argument](array.pop()));
            } else {
                let a = array.pop();
                let b = array.pop();
                array.push(new mapOperation[argument](b, a));
            }
        }
    }
    return array.pop();
}

// :NOTE: mapOperation?
const mapOperationChar = {"+": "+", "/": "/", "*": "*", "-": "-"};
const arrayOperationLong = ["negate", "product", "geom"];

function isDigit(x) {
    return (0 <= x) && (x <= 9);

}

function parseDigit(str) {
    let num = "";
    let j = 0;
    if (str[j] === "-") {
        num += "-";
        j++;
    }
    while (isDigit(str[j])) {
        num += str[j];
        j++;
    }
    return num;
}

function parseCloseBranches(str) {
    let array = [];
    for (let j = 0; j < str.length; j++) {
        if (variables[str[j]] !== undefined) {
            array.push(str[j]);
            j++;
        } else if (isDigit(str[j]) || str[j] === "-") {
            let num = parseDigit(str.substring(j));
            j += num.length;
            if (num === "-") {
                throw new Exception("invalid minus operator");
            }
            array.push(num);
        }
        if (mapBrackets[str[j]] === -1) {
            array.push(str[j]);
        } else if (mapBrackets[str[j]] === 1) {
            array.push(...parseOpenBranches(str.substring(j)));
            return array;
        } else if (variables[str[j - 1]] !== undefined) {
            return array;
        } else if (parseDigit(array[array.length - 1]) !== "") {
            return array;
        } else {
            throw new Exception("invalid operation " + str.substring(j));
        }
    }
    return array;
}

function parseOpenBranches(arg) {
    let array = [];
    let i;
    for (i = 0; i < arg.length; i++) {
        if (mapBrackets[arg[i]] === 1) {
            array.push(arg[i]);
            i++;
            if (mapOperationChar[arg[i]] !== undefined) {
                array.push(arg[i]);

            } else if (longOperation(arg.substring(i)) !== undefined) {
                let tempOperation = longOperation(arg.substring(i));
                i += tempOperation.length - 1;

                array.push(tempOperation);
            } else if (i === arg.length) {
                break;
            } else {
                throw new Exception("invalid operation " + arg.substring(i));
            }
        } else if (variables[arg[i]] !== undefined) {
            array.push(arg[i]);
        } else if (isDigit(arg[i]) || arg[i] === "-") {
            let a = parseDigit(arg.substring(i));
            array.push(a);
            i += a.length - 1;
        } else {
            throw new Exception("invalid operation " + arg.substring(i));
        }
    }

    return array;

}

function parsePrefix(str) {
    let argc = str.trim().split(/\s+/);
    if (str.length === 0) {
        throw new Exception("Empty expression");
    }
    let array = [];
    for (let arg of argc) {
        let counts = 0;
        for (let i = 0; i < arg.length; i++) {
            if (mapBrackets[arg[i]] === 1) {
                array.push(...parseOpenBranches(arg));
                break;
            } else if (mapBrackets[arg[i]] === -1) {
                array.push(...parseCloseBranches(arg));
                break;
            } else {
                counts++;
            }
        }
        if (counts === arg.length) {
            array.push(arg);
        }
    }
    return syntaxParser(array);
}

function longOperation(operation) {
    for (let op of arrayOperationLong) {
        if (operation.substring(0, op.length) === op) {
            return op;
        }
    }
    return undefined;
}

function syntaxParser(argc) {
    let result;
    for (let i = 0; i < argc.length; i++) {
        function part() {
            let arg = argc[i];
            if (isVarOrNum(arg)) {
                if (!isNaN(parseInt(arg))) {
                    if (String(parseInt(arg)) !== String(arg)) {
                        throw new Exception("invalid number expected " + parseInt(arg) + " actual " + (arg));
                    }
                    return new Const(parseInt(arg));
                } else {
                    return new Variable(arg);
                }
            } else if (mapBrackets[arg] !== undefined) {
                if (mapBrackets[arg] === -1) {
                    throw new Exception("Wrong number of parentheses " + argc.slice(i + 1));
                }
                i++;
                let b = part();
                if (mapBrackets[argc[i + 1]] === undefined || argc[i + 1] === "(") {
                    throw new Exception("Wrong number of parentheses " + argc.slice(i + 1));
                }
                i++;
                return b;
            } else {
                if (mapOperation[arg] === undefined) {
                    // :NOTE: 97 and 122 eto who?
                    if (arg.codePointAt(0) >= 97 && arg.codePointAt(0) <= 122) {
                        throw new Exception("invalid variable expected " + Object.keys(variables).join(', ') + " actual " + (arg));
                    }
                    if (arg === "") {
                        throw new Exception("invalid argument expected " + Object.keys(mapOperation).join(', ') + " actual " + (arg));
                    }
                    throw new Exception("invalid operation expected " + Object.keys(mapOperation).join(', ') + " actual " + (arg));
                }
                if (mapOperation[arg].countOf !== undefined) {
                    let arrayArgs = [];
                    if (i + mapOperation[arg].countOf >= argc.length) {
                        throw new Exception("Invalid argument error expected " + mapOperation[arg].countOf + " arguments");
                    }
                    if (mapOperation[arg].countOf === 0) {
                        let j = 0;
                        i++;
                        while (argc[i] !== ")") {
                            arrayArgs[j] = part();
                            i++;
                            j++;
                            if (argc[i] === ")") {
                                i--;
                                break;
                            }
                        }
                        if (j === 0) {
                            i--;
                        }
                    } else {
                        for (let j = 0; j < mapOperation[arg].countOf; j++) {
                            i++;
                            try {
                                arrayArgs[j] = part();
                            } catch (error) {
                                throw new Exception("Invalid argument error expected " + mapOperation[arg].countOf + " arguments");
                            }
                        }
                    }
                    return new mapOperation[arg](...arrayArgs);
                } else {
                    throw new Exception("Invalid argument error ");
                }
            }
        }

        result = part();
        if (i !== argc.length - 1) {
            throw new Exception("invalid arguments " + argc.slice(i));
        }
    }
    return result;
}

// let b = parsePrefix("(-y y 3 y)")
