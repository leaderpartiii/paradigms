package expression.exceptions;

import expression.General;
import expression.UnaryOperation;

import java.util.List;

public class Pow2 extends UnaryOperation {
    public Pow2(General rightSide) {
        super("2^", rightSide);
    }

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
        return "pow2" + "(" + rightSide + ")";
    }

    public int checkedOverflow(int x) {
        if (x >= 31) throw new OverflowException("overflow");
        if (x < 0) throw new IllegalArgumentException("pow2");
        return 1 << x;
    }
}
