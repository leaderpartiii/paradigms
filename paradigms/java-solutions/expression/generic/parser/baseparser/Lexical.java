package expression.generic.parser.baseparser;

import expression.exceptions.LexicalException;
import expression.generic.types.AbstractType;

import java.util.*;

import static expression.generic.parser.baseparser.LexemaType.*;
import static java.lang.Character.isWhitespace;

public class Lexical<T> {
    private final String source;
    private int pos = 0;
    private final List<Wrapper<T>> listLex = new ArrayList<>();
    private final List<String> variables;

    // :NOTE: HashMap instead of Map
    // :NOTE: Map.of()
    private static final Map<Character, LexemaType> OpenBrackets;

    static {
        OpenBrackets = Map.of('(', LEFTBRACKETCIRCLE);
    }

    private static final Map<LexemaType, LexemaType> OpenToCloseBrackets;

    static {
        OpenToCloseBrackets = Map.of(LEFTBRACKETCIRCLE, RIGHTBRACKETCIRCLE);
    }

    private static final Map<Character, LexemaType> CloseBrackets;

    static {
        CloseBrackets = Map.of(')', RIGHTBRACKETCIRCLE);
    }

    private static final HashMap<String, LexemaType> LongOperation = new HashMap<>();


    public Lexical(String expression) {
        this.source = expression;
        variables = List.of("x", "y", "z");
    }

    public Lexical(String expression, List<String> variables) {
        this.source = expression;
        this.variables = variables;
    }

    // :NOTE: code-style
    public List<Wrapper<T>> lexicalForm(AbstractType<T> type) throws LexicalException {
        while (pos < source.length()) {
            char i = source.charAt(pos);
            if (convert(i) == MINUS) {
                String arg = isDigit();
                if (!arg.equals("-")) {
                    if (!listLex.isEmpty() && !isOperationForMINUS(listLex.get(listLex.size() - 1).getLex())) {
                        listLex.add(new Lexema<>(PLUS, type.value(0)));
                    }
                    listLex.add(new Lexema<>(NUMBER, type.value(arg)));
                } else {
                    listLex.add(new Lexema<>(MINUS, type.value(0)));
                }
            } else if (convert(i) == CLEAR) {
                String word;
                if (!(word = isVariable(i + "")).isEmpty()) {
                    if (variables.equals(List.of("x", "y", "z"))) {
                        listLex.add(new SpecialArguments<>(VAR, variables.indexOf(word)));
                        pos++;
                    } else {
                        throw new LexicalException("unsupported variables");
                    }
                } else {
                    String s = isDigit();
                    if (!s.isEmpty()) {
                        listLex.add(new Lexema<T>(NUMBER, type.value(s)));
                    } else {
                        if (!isWhitespace(i)) {
                            throw new LexicalException("right variables or constants or actions, not in " + source.substring(pos));
                        }
                        pos++;
                    }
                }
            } else {
                listLex.add(new Lexema<>(convert(i), type.value(0)));
                pos++;
            }
        }
        listLex.add(new Lexema<>(LexemaType.EOL, type.value(0)));
        return listLex;
    }

    private boolean isOperationForMINUS(LexemaType t) {
        return (
                t == PLUS || t == MINUS || t == DIVIDE || t == MULTIPLY || t == LEFTBRACKETCIRCLE
        );
    }

    private LexemaType convert(char t) {
        return switch (t) {
            case ('-') -> MINUS;
            case ('+') -> PLUS;
            case ('/') -> DIVIDE;
            case ('*') -> MULTIPLY;
            case ('(') -> LEFTBRACKETCIRCLE;
            case (')') -> RIGHTBRACKETCIRCLE;
            default -> CLEAR;
        };
    }

    private boolean isOperation(char t) {
        return (
                t == '-' || t == '+' || t == '/' || t == '*' || t == '(' || t == ')'
        );
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
        } else if (i == '-' && pos + 1 < source.length() && source.charAt(pos + 1) != '-') {
            pos++;
            return ("-" + isDigit());
        } else if (i == '-' && (pos + 1 >= source.length() || source.charAt(pos + 1) == '-')) {
            pos++;
            return "-";
        }
        return (sb.toString());
    }

    boolean between(char i, char a, char b) {
        return (a <= i && i <= b);
    }

    public LexemaType isOpenBracket(char i) {
        return OpenBrackets.getOrDefault(i, CLEAR);
    }

    public static LexemaType isOpenBracket(LexemaType i) {
        return OpenToCloseBrackets.getOrDefault(i, CLEAR);
    }

    public static LexemaType isCloseBracket(char i) {
        return CloseBrackets.getOrDefault(i, CLEAR);
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
