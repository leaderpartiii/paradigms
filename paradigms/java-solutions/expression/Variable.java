package expression;


import java.util.List;

public class Variable extends Elements {
    protected String variable;
    protected int index;

    public Variable(String variable) {
        this.variable = variable;
    }

    public Variable(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return (variable != null ? variable : "$" + (index));
    }

    public int evaluate(int x) {
        return super.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (variable) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass() == Variable.class && ((Variable) o).getVariable().equals(variable);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        // :NOTE: custom error if ArrayIndexOutOfBounds
        return variables.get(index);
    }

    @Override
    public int hashCode() {
        return variable.charAt(0) == 0 ? 271 * 101 : variable.charAt(0) * 241;
    }
}
