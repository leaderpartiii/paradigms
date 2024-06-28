package expression.exceptions;

// :NOTE: все ошибки вычислителя (IllegalArgumentException, OverflowException, DivideByZeroException)
// должны наследоваться от одного общего ArithmeticException: так легче отлавливать все ошибки вычисления
public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String e) {
        super("Incorrect argument in " + e + " action");
    }
}
