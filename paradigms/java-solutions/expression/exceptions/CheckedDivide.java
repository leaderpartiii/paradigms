package expression.exceptions;

import expression.Divide;
import expression.General;

import java.util.List;

public class CheckedDivide extends Divide {
    public CheckedDivide(General leftSide, General rightSide) {
        super(leftSide, rightSide);
    }

    public int evaluate(int x) /*throws Exception*/ {
        return checkedOverflow(leftSide.evaluate(x), rightSide.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return checkedOverflow(leftSide.evaluate(x, y, z), rightSide.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return checkedOverflow(leftSide.evaluate(variables), rightSide.evaluate(variables));
    }

    public static int checkedOverflow(int left, int right) {
        if (right == 0) throw new DivideByZeroException("divide by zero");
        if (left == Integer.MIN_VALUE && right == -1) throw new OverflowException("overflow");
        return left / right;
    }
}
