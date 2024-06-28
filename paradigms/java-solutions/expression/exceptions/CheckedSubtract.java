package expression.exceptions;

import expression.General;
import expression.Subtract;

import java.util.List;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(General leftSide, General rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int evaluate(int x) {
        return checkedOverflow(leftSide.evaluate(x), rightSide.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return checkedOverflow(leftSide.evaluate(x, y, z), rightSide.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return checkedOverflow(leftSide.evaluate(variables), rightSide.evaluate(variables));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public static int checkedOverflow(int left, int right) /*throws Exception*/ {
        // :NOTE: переполнение
        if (left >= 0 && right < 0 && left > Integer.MAX_VALUE + right) throw new OverflowException("overflow");
        if (left <= 0 && right > 0 && left < Integer.MIN_VALUE + right) throw new OverflowException("overflow");
        return left - right;
    }
}
