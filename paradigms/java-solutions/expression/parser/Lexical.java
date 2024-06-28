package expression.parser;

import java.util.*;

import static expression.parser.LexemaType.*;
import static java.lang.Character.isWhitespace;

import expression.exceptions.LexicalException;

public class Lexical {
    // :NOTE: private
    String source;
    // :NOTE: убрать балансы
    private int pos = 0;
    private String word;
    private final List<Lexema> listLex = new ArrayList<>();
    private final List<String> variables;

    // :NOTE: private? (относится почти ко всем полям и методам ниже)
    static HashMap<Character, LexemaType> OpenBrackets = new HashMap<>() {{
        put('(', left_bracket_circle);
        put('[', left_bracket_curly);
        put('{', left_bracket_square);
//            put('(', new Lexema(left_bracket_circle, "("));
    }};

    static HashMap<LexemaType, LexemaType> OpenToCloseBrackets = new HashMap<>() {{
        put(left_bracket_circle, right_bracket_circle);
        put(left_bracket_curly, right_bracket_curly);
        put(left_bracket_square, right_bracket_square);
    }};

    static HashMap<Character, LexemaType> CloseBrackets = new HashMap<>() {{
        put(')', right_bracket_circle);
        put('}', right_bracket_curly);
        put(']', right_bracket_square);
    }};
    static HashMap<String, LexemaType> LongOperation = new HashMap<>() {{
        put("pow2", pow2);
        put("log2", log2);
        put("shifts", null);
    }};


    public Lexical(String expression) {
        // :NOTE: + " "?
        this.source = expression;
        variables = List.of("x", "y", "z");
    }

    public Lexical(String expression, List<String> variables) {
        this.source = expression;
        this.variables = variables;
    }

    // :NOTE: naming
    public List<Lexema> lexical_form() throws LexicalException {
        while (pos < source.length()) {
            char i = source.charAt(pos);
            // :NOTE: очень много копипасты
            if (i == '(') {
                listLex.add(new Lexema(left_bracket_circle, i));
                pos++;
            } else if (i == '[') {
                listLex.add(new Lexema(left_bracket_square, i));
                pos++;
            } else if (i == '{') {
                listLex.add(new Lexema(left_bracket_curly, i));
                pos++;
            } else if (i == '}') {
                listLex.add(new Lexema(right_bracket_curly, i));
                pos++;
            } else if (i == ']') {
                listLex.add(new Lexema(right_bracket_square, i));
                pos++;
            } else if (i == ')') {
                listLex.add(new Lexema(right_bracket_circle, i));
                pos++;
            } else if (i == '*') {
                listLex.add(new Lexema(multiply, i));
                pos++;
            } else if (i == '/') {
                listLex.add(new Lexema(divide, i));
                pos++;
            } else if (i == '+') {
                listLex.add(new Lexema(plus, i));
                pos++;
            } else if (i == '&') {
                listLex.add(new Lexema(and, i));
                pos++;
            } else if (i == '^') {
                listLex.add(new Lexema(xor, i));
                pos++;
            } else if (i == '|') {
                listLex.add(new Lexema(or, i));
                pos++;
            } else if (i == '~') {
                listLex.add(new Lexema(not, i));
                pos++;
            } else if (i == 'p') {
                PowLog2("pow2");
            } else if (i == 'l') {
                PowLog2("log2");
            } else if (i == '-') {
                String arg = isDigit();
                if (!arg.equals("-")) {
                    if (!listLex.isEmpty() && !isOperationForMinus(listLex.get(listLex.size() - 1).getLex())) {
                        listLex.add(new Lexema(plus, i));
                    }
                    listLex.add(new Lexema(number, arg));
                } else {
                    listLex.add(new Lexema(minus, i));
                }
            } else if (!(word = isVariable(i + "")).isEmpty()) {
                if (variables.equals(List.of("x", "y", "z"))) {
                    listLex.add(new Lexema(var, word));
                    pos++;
                } else {
                    listLex.add(new Lexema(var, variables.indexOf(word)));
                }
            } else {
                String s = isDigit();
                if (!s.isEmpty()) {
                    listLex.add(new Lexema(number, s));
                } else {
                    if (!isWhitespace(i)) {
                        throw new LexicalException("right variables or constants or actions, not in " + source.substring(pos));
                    }
                    pos++;
                }
            }
        }
        listLex.add(new Lexema(LexemaType.eol, '\0'));
        return listLex;
    }

    // :NOTE: private?
    public boolean isOperationForMinus(LexemaType t) {
        return (
                t == or || t == xor || t == and || t == not
                        || t == plus || t == minus || t == divide || t == multiply
                        || t == left_bracket_circle || t == left_bracket_curly || t == left_bracket_square
        );
    }

    public boolean isOperation(char t) {
        return (t == '^' || t == '|' || t == '&' || t == '~' ||
                t == '-' || t == '/' || t == '(' || t == ')' ||
                t == '{' || t == '}' || t == '[' || t == ']' || t == '*' || t == '+');
    }

    public String isDigit() {
        char i = source.charAt(pos);
        StringBuilder sb = new StringBuilder();
        if (between(i, '0', '9')) {
            do {
                sb.append(i);
                pos++;
                if (pos >= source.length()) {
                    break;
                }
                i = source.charAt(pos);
            } while (between(i, '0', '9'));
        } else if (i == '-' && pos + 1 <source.length() && source.charAt(pos + 1) != '-') {
            pos++;
            return "-" + isDigit();
        } else if (i == '-' && (pos + 1 >=source.length() || source.charAt(pos + 1) == '-')) {
            pos++;
            return "-";
        }
        return sb.toString();
    }

    boolean between(char i, char a, char b) {
        return (a <= i && i <= b);
    }

    public void PowLog2(String arg) throws LexicalException {
        // :NOTE: нерасширяемо
//        if (pos + 4 < source.length() && (Character.toString(source.charAt(pos)) + (source.charAt(pos + 1)) + (source.charAt(pos + 2)) + (source.charAt(pos + 3))).equals(arg)) {
        if (!isLongOperation(arg).isEmpty()) {
            char i = source.charAt(pos);
            // :NOTE: тип лексемы из мапы
            listLex.add(new Lexema(LongOperation.get(arg), arg));
            if (isOpenBracket(i) != clear) {
                listLex.add(new Lexema(isOpenBracket(i), i));
                pos++;
            } else if (i != ' ') {
                throw new LexicalException("right " + arg + " form");
            } else if (between(source.charAt(pos + 1), '0', '9') || source.charAt(pos + 1) == '-') {
                pos++;
                String number = isDigit();
                if (!Objects.equals(number, "-")) {
                    listLex.add(new Lexema(LexemaType.number, number));
                } else {
                    listLex.add(new Lexema(minus, number));
                }
            } else if (!(word = isVariable(source.charAt(pos + 1) + "")).isEmpty()) {
                pos++;
                listLex.add(new Lexema(LexemaType.var, word));
                pos++;
            }
        } else {
            throw new LexicalException(arg + " action");
        }
    }

    public String isLongOperation(String arg) {
        StringBuilder sb = new StringBuilder();
        if (pos + arg.length() < source.length()) {
            for (int i = 0; i < arg.length(); i++) {
                sb.append(source.charAt(pos + i));
            }
            if (sb.toString().equals(arg)) {
                pos += arg.length();
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public LexemaType isOpenBracket(char i) {
        return OpenBrackets.getOrDefault(i, clear);
    }

    public static LexemaType isOpenBracket(LexemaType i) {
        // :NOTE: завести map из скобок
        return OpenToCloseBrackets.getOrDefault(i, clear);
    }

    public static LexemaType isCloseBracket(char i) {
        return CloseBrackets.getOrDefault(i, clear);
    }

    public static boolean isCloseBracket(LexemaType i) {
        return CloseBrackets.containsValue(i);
    }

    public String isVariable(String var) throws LexicalException {
        if (variables.contains(var)) {
            return var;
        } else if (Objects.equals(var, "$")) {
            char i = source.charAt(pos);
            StringBuilder sb = new StringBuilder();
            if (!isWhitespace(i) && !isOperation(i)) {
                do {
                    sb.append(i);
                    pos++;
                    if (pos >= source.length()) {
                        break;
                    }
                    i = source.charAt(pos);
                } while (!isWhitespace(i) && !isOperation(i));
                if (variables.contains(sb.toString())) {
                    return sb.toString();
                } else {
                    throw new LexicalException("Correct variable");
                }
            } else {
                throw new LexicalException("Correct variable");
            }
        }
        return "";
    }
}
