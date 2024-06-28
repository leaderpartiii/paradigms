package expression.generic.parser.baseparser;

abstract public class Wrapper<T>  {
    abstract public LexemaType getLex();
    abstract public T getValue();
    abstract public int getInteger();
}
