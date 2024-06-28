package expression.generic;

import expression.exceptions.LexicalException;
import expression.generic.parser.baseparser.ExpressionParser;
import expression.generic.parser.baseparser.TripleExpression;
import expression.generic.types.*;


public class GenericTabulator implements Tabulator {
    // :NOTE: throws Exception
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws LexicalException {
        AbstractType<?> type = switch (mode) {
            case "d" -> new DoubleType();
            case "bi" -> new BigIntType();
            case "i" -> new IntegerType();
            case "u" -> new UnchekedIntegerType();
            case "b" -> new ByteType();
            case "bool" -> new BoolType();
            default -> throw new IllegalArgumentException("Doesn't have mode: " + mode);
        };
        return evaluate(expression, type.getType(), x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] evaluate(String expression, AbstractType<T> type, int x1, int x2, int y1, int y2, int z1, int z2) throws LexicalException {
        TripleExpression<T> tripleExpression = new ExpressionParser<>(type).parse(expression);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        result[i][j][k] = tripleExpression.evaluate(type.value(x1 + i), type.value(y1 + j), type.value(z1 + k));
                    } catch (Exception e) {
                        // :NOTE: catch Exception
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }

}
