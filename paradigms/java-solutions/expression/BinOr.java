package expression;

import java.util.List;

public class BinOr extends Operation {
    String operation = "|";

    public BinOr(General leftSide, General rightSide) {
        super(leftSide, rightSide);
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public int evaluate(int x) {
        return leftSide.evaluate(x) | rightSide.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftSide.evaluate(x, y, z) | rightSide.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + leftSide+ " " + operation +" "+ rightSide + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == BinOr.class) {
            return leftSide.equals(((Operation) o).getLeftSide()) && rightSide.equals(((Operation) o).getRightSide());
        } else {
            return false;
        }
    }
    @Override
    public int evaluate(List<Integer> variables) {
        return leftSide.evaluate(variables) | rightSide.evaluate(variables);
    }
}
