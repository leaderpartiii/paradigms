package expression;

import java.util.List;
import java.util.Objects;

public class Const extends Elements {
    protected int cons;

    public Const(int cons) {
        this.cons = (cons);
    }

    @Override
    public String toString() {
        return Integer.toString(cons);
    }

    @Override
    public int evaluate(int x) {
        return cons;

    }
    @Override
    public int evaluate(int x, int y, int z) {
        return cons;
    }

    public int getCons() {
        return cons;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass() == Const.class && Objects.equals(((Const) o).getCons(), cons);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return cons;
    }

    @Override
    public int hashCode() {
        return (cons) == 0 ? 1923 : (char) cons * (-2003);
    }
}
