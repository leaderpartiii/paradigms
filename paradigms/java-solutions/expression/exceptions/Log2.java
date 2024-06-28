package expression.exceptions;

import expression.General;
import expression.UnaryOperation;

import java.util.List;

public class Log2 extends UnaryOperation {

    public Log2(General rightSide) {
        super("log2", rightSide);
    }

    @Override
    public int evaluate(int x) {
        return checkedOverflow(rightSide.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return checkedOverflow(rightSide.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return 0;
    }

    @Override
    public String toString() {
        return "log2" + "(" + rightSide.toString() + ")";
    }

    public int checkedOverflow(int x) {
        if (x <= 0) throw new IllegalArgumentException("log2");
        if (x == 1) return 0;
        int count = 0;
        while (x > 1) {
            x = x >> 1;
            count++;
        }
        return count;
    }
}
