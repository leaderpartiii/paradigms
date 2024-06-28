package expression.parser;

import java.util.List;

public class LexicalSource {
    protected List<Lexema> lexicalSource;
    protected int pos;

    public LexicalSource(List<Lexema> lexicalSource) {
        this.lexicalSource = lexicalSource;
    }

    public Lexema next() {
        return lexicalSource.get(pos++);
    }

    public void back() {
        pos--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = (0 <= pos - 2 ? pos - 2 : pos - 1); i < lexicalSource.size(); i++) {
            sb.append(lexicalSource.get(i).getLex()).append(" ");
        }
        return sb.toString();
    }
}
