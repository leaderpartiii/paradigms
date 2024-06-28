package expression.generic.parser;

import expression.generic.types.AbstractType;

public class Negate<T> extends AbstractUnary<T> {

    public Negate(GeneralExpression<T> a, AbstractType<T> type) {
        super(a, type);
    }

    @Override
    protected T calculate(T a) {
        return type.valueMinus(a);
    }
}
