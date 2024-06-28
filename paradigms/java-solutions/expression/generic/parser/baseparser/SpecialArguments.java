package expression.generic.parser.baseparser;

public class SpecialArguments<T> extends Wrapper<T> {
    protected int a;
    protected LexemaType b;

    public SpecialArguments(LexemaType b, int a) {
        this.b = b;
        this.a = a;
    }

    public int getInteger() {
        return a;
    }
    public LexemaType getLex() {
        return b;
    }

    @Override
    public T getValue() {
        return null;
    }
}
