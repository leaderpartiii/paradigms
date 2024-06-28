package expression;


import java.util.List;

public class Subtract extends Operation {
    protected String operation = "-";
    public Subtract(General leftSide, General rightSide) {
        super(leftSide, rightSide);
    }

    @Override
    public String toString() {
        return super.toString(operation);
    }

    public int evaluate(int x) {
        return leftSide.evaluate(x) - rightSide.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftSide.evaluate(x, y, z) - rightSide.evaluate(x, y, z);
    }

    @Override
    public int hashCode() {
        int hash = leftSide.hashCode() * 199 + rightSide.hashCode() * 179;
        return hash - 1931;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == Subtract.class) {
            return leftSide.equals(((Operation) o).getLeftSide()) && rightSide.equals(((Operation) o).getRightSide());
        } else {
            return false;
        }
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return leftSide.evaluate(variables) - rightSide.evaluate(variables);
    }
}
