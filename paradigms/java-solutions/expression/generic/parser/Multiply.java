package expression.generic.parser;

import expression.generic.types.AbstractType;

public class Multiply<T> extends AbstractOperation<T> {
    public Multiply(GeneralExpression<T> a, GeneralExpression<T> b, AbstractType<T> type) {
        super(a, b, type);
    }


    @Override
    protected T calculate(T a, T b) {
        return type.multiply(a, b);
    }
}
