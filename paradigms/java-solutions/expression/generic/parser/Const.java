package expression.generic.parser;

import expression.generic.types.AbstractType;

import java.math.BigInteger;

public class Const<T> extends AbstractValue<T> {
    T a;
    public Const(T a, AbstractType<T> type) {
        super(type);
        this.a = a;
    }

    // :NOTE: string storage is slow
    @Override
    protected T calculate(T x, T y, T z) {
        return type.value(a);
    }
}
