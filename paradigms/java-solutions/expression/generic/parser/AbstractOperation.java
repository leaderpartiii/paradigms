package expression.generic.parser;

import expression.generic.types.AbstractType;

abstract public class AbstractOperation<T> extends GeneralExpression<T> {

    protected GeneralExpression<T> a;
    protected GeneralExpression<T> b;
    protected AbstractType<T> type;

    public AbstractOperation(GeneralExpression<T> a, GeneralExpression<T> b, AbstractType<T> type) {
        this.a = a;
        this.b = b;
        this.type = type;
    }

    public T evaluate(T x, T y, T z) {
        return calculate(a.evaluate(x, y, z), b.evaluate(x, y, z));
    }

    abstract protected T calculate(T a, T b);
}
