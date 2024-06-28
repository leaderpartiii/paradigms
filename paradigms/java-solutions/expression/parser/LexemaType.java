package expression.parser;

public enum LexemaType {
    minus, plus,
    multiply, divide,
    left_bracket_circle, right_bracket_circle, left_bracket_square, right_bracket_square, left_bracket_curly, right_bracket_curly,
    number, var,
    unary, not, pow2, log2,
    and, xor, or,
    clear,
    eol;
}
