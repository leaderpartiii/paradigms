package expression;


import java.util.List;

public class Add extends Operation {
    // :NOTE: но Operation уже хранит lhs, rhs
    protected General leftSide;
    // :NOTE: и наверное все же private
    protected General rightSide;
    protected String operation = "+";

    public Add(General leftSide, General rightSide) {
        super(leftSide, rightSide);
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return super.toString(operation);
    }

    public int evaluate(int x) {
        return leftSide.evaluate(x) + rightSide.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftSide.evaluate(x, y, z) + rightSide.evaluate(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == Add.class) {
            return leftSide.equals(((Operation) o).getLeftSide()) && rightSide.equals(((Operation) o).getRightSide());
        } else {
            return false;
        }
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return leftSide.evaluate(variables) + rightSide.evaluate(variables);
    }

    @Override
    public int hashCode() {
        return leftSide.hashCode() * rightSide.hashCode() + 79 - rightSide.hashCode();
    }

}
