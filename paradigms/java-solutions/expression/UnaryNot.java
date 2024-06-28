package expression;

import java.util.List;

public class UnaryNot extends UnaryOperation {
    public UnaryNot(General rightSide) {
        super("~", rightSide);
    }

    @Override
    public int evaluate(int x) {
        return super.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return super.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return ~rightSide.evaluate(variables);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
