package expression.generic.types;


import expression.exceptions.*;
import expression.exceptions.IllegalArgumentException;

import java.lang.Integer;

public class IntegerType implements AbstractType<Integer> {
    @Override
    public Integer add(Integer a, Integer b) {
        return CheckedAdd.checkedOverflow(a, b);
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return CheckedMultiply.checkedOverflow(a, b);
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        return CheckedDivide.checkedOverflow(a, b);
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return CheckedSubtract.checkedOverflow(a, b);
    }

    @Override
    public Integer value(int a) {
        return a;
    }

    @Override
    public Integer value(String a) {
        try {
            return Integer.parseInt(a);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(a + "it is doesn't int");
        }
    }

    @Override
    public Integer value(Integer a) {
        return a;
    }

    @Override
    public Integer valueMinus(Integer a) {
        return CheckedNegate.checkedOverflow(a);
    }

    public AbstractType<?> getType() {
        return new IntegerType();
    }
}
