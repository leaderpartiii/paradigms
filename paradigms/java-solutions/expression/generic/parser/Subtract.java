package expression.generic.parser;

import expression.generic.types.AbstractType;

public class Subtract<T> extends AbstractOperation<T> {
    public Subtract(GeneralExpression<T> a, GeneralExpression<T> b, AbstractType<T> abstractType) {
        super(a, b, abstractType);
    }

    @Override
    protected T calculate(T a, T b) {
        return type.subtract(a, b);
    }
}
