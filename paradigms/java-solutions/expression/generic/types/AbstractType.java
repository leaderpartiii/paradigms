package expression.generic.types;


public interface AbstractType<T> {
    T add(T a, T b);
    T multiply(T a, T b);
    T divide(T a, T b);
    T subtract(T a, T b);
    T value(int a);
    T value(String a);
    T value(T a);
    T valueMinus(T a);
    AbstractType<?> getType();
}
