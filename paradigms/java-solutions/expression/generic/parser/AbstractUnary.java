package expression.generic.parser;

import expression.generic.types.AbstractType;

abstract public class AbstractUnary<T> extends GeneralExpression<T> {
    protected GeneralExpression<T> a;
    protected AbstractType<T> type;

    public AbstractUnary(GeneralExpression<T> a, AbstractType<T> type) {
        this.a = a;
        this.type = type;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return calculate(a.evaluate(x, y, z));
    }

    abstract protected T calculate(T a);
}
