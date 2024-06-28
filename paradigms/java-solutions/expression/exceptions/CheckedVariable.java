package expression.exceptions;

import expression.Variable;

import java.util.List;

public class CheckedVariable extends Variable {
    public CheckedVariable(String variable) {
        super(variable);
    }

    public CheckedVariable(int index) {
        super(index);
        if (index<0) throw new IllegalArgumentException("variable index");
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int evaluate(String a) {
        try {
            return super.evaluate(Integer.parseInt(a));
        } catch (NumberFormatException e) {
            throw new OverflowException("integer overflow");
        }
    }
    public int evaluate(String x, String y, String z) {
        try {
            return super.evaluate(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
        } catch (NumberFormatException e) {
            throw new OverflowException("integer overflow");
        }
    }

    @Override
    public int evaluate(List<Integer> variables) {
        // :NOTE: своя ошибка
//        for (Integer integer : variables) {
//            if (integer < 0) {
//                throw new IllegalArgumentException("variable list");
//            }
//        }
        return super.evaluate(variables);
    }

    @Override
    public String getVariable() {
        return super.getVariable();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
