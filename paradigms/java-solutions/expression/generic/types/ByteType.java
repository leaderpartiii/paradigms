package expression.generic.types;

public class ByteType implements AbstractType<Byte> {
    @Override
    public Byte add(Byte a, Byte b) {
        return (byte) (a + b);
    }

    @Override
    public Byte multiply(Byte a, Byte b) {
        return (byte) (a * b);
    }

    @Override
    public Byte divide(Byte a, Byte b) {
        return (byte) (a / b);
    }

    @Override
    public Byte subtract(Byte a, Byte b) {
        return (byte) (a - b);
    }

    @Override
    public Byte value(int a) {
        return (byte) a;
    }

    @Override
    public Byte value(Byte a) {
        return a;
    }

    @Override
    public Byte value(String a) {
        return Byte.parseByte(a);
    }

    @Override
    public Byte valueMinus(Byte a) {
        return (byte) -a;
    }

    @Override
    public AbstractType<?> getType() {
        return new ByteType();
    }
}
