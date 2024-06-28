package expression.generic.types;

public class UnchekedIntegerType implements AbstractType<Integer> {
    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer value(int a) {
        return a;
    }

    @Override
    public Integer value(String a) {
        return Integer.parseInt(a);
    }

    @Override
    public Integer value(Integer a) {
        return a;
    }

    @Override
    public Integer valueMinus(Integer a) {
        return -a;
    }

    @Override
    public AbstractType<?> getType() {
        return new UnchekedIntegerType();
    }
}
