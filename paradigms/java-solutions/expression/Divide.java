package expression;

import java.util.List;

public class Divide extends Operation {
    protected String operation = "/";
    public Divide(General leftSide, General rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    public String toString() {
        return super.toString(operation);
    }
    @Override
    public int evaluate(int x) {
        return leftSide.evaluate(x) / rightSide.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftSide.evaluate(x, y, z) / rightSide.evaluate(x, y, z);
//        return super.evaluate(x, y, z, "/");
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == Divide.class) {
            return leftSide.equals(((Operation) o).getLeftSide()) && rightSide.equals(((Operation) o).getRightSide());
        } else {
            return false;
        }
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return leftSide.evaluate(variables) / rightSide.evaluate(variables);
    }

    @Override
    public int hashCode() {
        int hash = leftSide.hashCode() + 23 - rightSide.hashCode() * 2003;
        return hash - 1357;
    }

}
