package expression.exceptions;

import expression.Elements;

import java.util.List;

public class CheckedConst extends Elements {
    public int elem;
    public CheckedConst(String cons) {
        try {
            this.elem = Integer.parseInt(cons);
        } catch (NumberFormatException e) {
            throw new OverflowException("int overflow");
        }
    }
    @Override
    public int evaluate(int c) {
        return elem;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return elem;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return elem;
    }

    @Override
    public String toString() {
        return Integer.toString(elem);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
