package expression.generic.parser;

import expression.exceptions.IllegalArgumentException;
import expression.generic.types.AbstractType;

public class Variable<T> extends AbstractValue<T> {
    Integer a;
    public Variable(Integer a, AbstractType<T> type) {
        super(type);
        this.a = a;
    }

    @Override
    protected T calculate(T x, T y, T z) {
        if (a == 0) {
            return x;
        } else if (a == 1) {
            return y;
        } else if (a == 2) {
            return z;
        } else {
            throw new IllegalArgumentException("Doesn't be variable " + a);
        }
    }
}
