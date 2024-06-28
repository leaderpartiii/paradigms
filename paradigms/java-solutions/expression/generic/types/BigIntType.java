package expression.generic.types;

import java.math.BigInteger;

public class BigIntType implements AbstractType<BigInteger> {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger value(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    public BigInteger value(BigInteger a) {
        return a;
    }

    @Override
    public BigInteger value(String a) {
        return new BigInteger(a);
    }

    @Override
    public BigInteger valueMinus(BigInteger a) {
        return a.negate();
    }

    @Override
    public AbstractType<?> getType() {
        return new BigIntType();
    }
}
