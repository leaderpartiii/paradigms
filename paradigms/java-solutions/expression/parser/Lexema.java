package expression.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexema {
    protected LexemaType lex;
    protected String value;
    protected int index;

    public Lexema(LexemaType lex, String value) {
        this.lex = lex;
        this.value = value;
    }

    public Lexema(LexemaType lex, char value) {
        this.lex = lex;
        this.value = Character.toString(value);
    }

    public Lexema(LexemaType lex, int value) {
        this.lex = lex;
        this.index = value;
    }

    public LexemaType getLex() {
        return lex;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Lexema{" +
                "lex=" + lex +
                ", value='" + (value != null ? value : index) + '\'' +
                '}';
    }
}
