package expression.exceptions;

import expression.General;
import expression.Multiply;

import java.util.List;

public class CheckedMultiply extends Multiply {


    public CheckedMultiply(General leftSide, General rightSide) {
        super(leftSide, rightSide);
    }

    public int evaluate(int x) {
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
        if ((right != 0) &&
                ((right > 0 && left > 0 && left > Integer.MAX_VALUE / right) ||
                (right < 0 && left < 0 && left < Integer.MAX_VALUE / right) ||
                (right < -1 && left > 0 && left > Integer.MIN_VALUE / right) ||
                (right > 0 && left < 0 && left < Integer.MIN_VALUE / right))
                ) {
            throw new OverflowException("overflow");
        }
        return left * right;
    }
}
