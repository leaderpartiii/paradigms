package expression;


abstract class Operation implements General {
    // :NOTE: почему protected? private final
    protected General leftSide;
    protected General rightSide;

    public Operation(General leftSide, General rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public String toString(String s) {
        return "(" + leftSide + " " + s + " " + rightSide + ")";
    }

    public General getLeftSide() {
        return leftSide;
    }

    public General getRightSide() {
        return rightSide;
    }

}
