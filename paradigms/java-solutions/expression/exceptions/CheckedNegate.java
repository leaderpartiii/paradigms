package expression.exceptions;

import expression.General;
import expression.UnaryMinus;

import java.util.List;

public class CheckedNegate extends UnaryMinus {
    public CheckedNegate(General rightSide) {
        super("-", rightSide);
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
        return checkedOverflow(rightSide.evaluate(variables));
    }
    @Override
    public String toString() {
        return super.toString();
    }
    public static int checkedOverflow(int right) /*throws Exception*/ {
        if (right == Integer.MIN_VALUE)
            throw new OverflowException("overflow");
        return -right;
    }
}
