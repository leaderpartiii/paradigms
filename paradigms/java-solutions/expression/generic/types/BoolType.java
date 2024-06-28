package expression.generic.types;

import expression.exceptions.DivideByZeroException;

public class BoolType implements AbstractType<Boolean> {
    @Override
    public Boolean add(Boolean a, Boolean b) {
        return Boolean.logicalOr(a, b);
    }

    @Override
    public Boolean multiply(Boolean a, Boolean b) {
        return Boolean.logicalAnd(a, b);
    }

    @Override
    public Boolean divide(Boolean a, Boolean b) {
        if (!b) {
            throw new DivideByZeroException("argument b doesn't be 0");
        }
        return a;
    }

    @Override
    public Boolean subtract(Boolean a, Boolean b) {
        return Boolean.logicalXor(a, b);
    }

    @Override
    public Boolean value(int a) {
        return (a != 0);
    }

    @Override
    public Boolean value(String a) {
        return !a.equals("0");
    }

    @Override
    public Boolean value(Boolean a) {
        return a;
    }

    @Override
    public Boolean valueMinus(Boolean a) {
        return a;
    }

    @Override
    public AbstractType<?> getType() {
        return new BoolType();
    }
}
