package expression.generic.parser;

import expression.generic.types.AbstractType;

public class Add<T> extends AbstractOperation<T> {
    public Add(GeneralExpression<T> a, GeneralExpression<T> b, AbstractType<T> abstractType) {
        super(a, b, abstractType);
    }

    @Override
    protected T calculate(T a, T b) {
        return type.add(a, b);
    }

}
