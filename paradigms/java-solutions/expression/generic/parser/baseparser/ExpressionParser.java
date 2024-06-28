package expression.generic.parser.baseparser;


import expression.exceptions.LexicalException;
import expression.generic.types.AbstractType;

public class ExpressionParser<T> implements TripleParser<T> {
    AbstractType<T> type;

    public ExpressionParser(AbstractType<T> type) {
        this.type = type;
    }

    @Override
    public TripleExpression<T> parse(String expression) throws LexicalException {
        return new ExpressionParsing<>(type).parse(
                new LexicalSource<>(
                        new Lexical<T>(expression).lexicalForm(type)
                )
        );
    }
}
