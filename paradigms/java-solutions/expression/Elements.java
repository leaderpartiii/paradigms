package expression;


public abstract class Elements implements General {
    protected String elem;

    @Override
    public int evaluate(int c) {
        return c;
    }
    @Override
    public int hashCode() {
        return elem != null ? elem.hashCode() : 0;
    }
}
