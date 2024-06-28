package expression;


abstract public class UnaryOperation implements General {
    // :NOTE: private
    String op;
    // :NOTE: private
    public General rightSide;

    public UnaryOperation(String op, General rightSide) {
        this.op = op;
        this.rightSide = rightSide;
    }

    public int evaluate(int x) {
        if (op.equals("-")) {
            return -rightSide.evaluate(x);
        } else if (op.equals("~")) {
            return ~rightSide.evaluate(x);
        } else {
            throw new RuntimeException("Unknown opertaion");
        }
    }
    @Override
    public int evaluate(int x, int y, int z) {
        if (op.equals("-")) {
            return -rightSide.evaluate(x, y, z);
        } else if (op.equals("~")) {
            return ~rightSide.evaluate(x, y, z);
        } else {
            throw new RuntimeException("Unknown opertaion");
        }
    }

    @Override
    public String toString() {
        return op + "(" + rightSide + ")";
    }

}
