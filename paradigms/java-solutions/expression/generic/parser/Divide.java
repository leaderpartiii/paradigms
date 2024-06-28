package expression.generic.parser;

import expression.generic.types.AbstractType;

public class Divide<T> extends AbstractOperation<T> {

    public Divide(GeneralExpression<T> a, GeneralExpression<T> b, AbstractType<T> type) {
        super(a, b, type);
    }

    @Override
    protected T calculate(T a, T b) {
        return type.divide(a, b);
    }
}
