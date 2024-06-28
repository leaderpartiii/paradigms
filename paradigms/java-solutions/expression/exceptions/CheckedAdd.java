package expression.exceptions;

import expression.Add;
import expression.General;

import java.util.List;

public class CheckedAdd extends Add {

    public CheckedAdd(General leftSide, General rightSide) {
        super(leftSide, rightSide);
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
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static int checkedOverflow(int left, int right) {
        if (left > 0 && Integer.MAX_VALUE - left < right) throw new OverflowException("int overflow");
        if (left < 0 && Integer.MIN_VALUE - left > right) throw new OverflowException("int overflow");
        return left + right;
    }
}
