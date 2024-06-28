package expression.generic.parser.baseparser;

import expression.generic.types.IntegerType;

public class Lexema<T> extends Wrapper<T> {
    protected LexemaType lex;
    protected T value;
    protected int index;

    public Lexema(LexemaType lex, T value) {
        this.lex = lex;
        this.value = value;
    }
    public LexemaType getLex() {
        return lex;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int getInteger() {
        return -1;
    }
    public int getIndex() {
        return index;
    }
}
