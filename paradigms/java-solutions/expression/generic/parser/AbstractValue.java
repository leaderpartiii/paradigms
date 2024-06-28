package expression.generic.parser;

import expression.generic.types.AbstractType;

abstract public class AbstractValue<T> extends GeneralExpression<T> {
    // :NOTE: modifiers?
    protected AbstractType<T> type;

    public AbstractValue(AbstractType<T> type) {
        this.type = type;
    }

    public T evaluate(T x, T y, T z) {
        return calculate(x, y, z);
    }

    abstract protected T calculate(T x, T y, T z);
}
