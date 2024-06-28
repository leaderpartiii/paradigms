package expression.generic.types;

public class DoubleType implements AbstractType<Double> {
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double value(int a) {
        return (double) a;
    }

    @Override
    public Double value(String a) {
        return Double.valueOf(a);
    }


    @Override
    public Double value(Double a) {
        return a;
    }

    @Override
    public Double valueMinus(Double a) {
        return -a;
    }

    public AbstractType<?> getType() {
        return new expression.generic.types.DoubleType();
    }
}
