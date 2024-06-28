package expression;

import java.util.List;

public class Multiply extends Operation {
    protected String operation = "*";

    public Multiply(General leftSide, General rightSide) {
        super(leftSide, rightSide);
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return "(" + leftSide+ " " + operation +" "+ rightSide + ")";
    }

    @Override
    public int evaluate(int x) {
        return leftSide.evaluate(x) * rightSide.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftSide.evaluate(x, y, z) * rightSide.evaluate(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == Multiply.class) {
            return leftSide.equals(((Operation) o).getLeftSide()) && rightSide.equals(((Operation) o).getRightSide());
        } else {
            return false;
        }
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return leftSide.evaluate(variables) * rightSide.evaluate(variables);

    }

    @Override
    public int hashCode() {
        int result = rightSide != null ? rightSide.hashCode() : 0;
        result = 31 * result + (leftSide != null ? leftSide.hashCode() : 0);
        return result + 23;
    }
}
