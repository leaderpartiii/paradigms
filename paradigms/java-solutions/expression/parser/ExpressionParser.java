package expression.parser;

import expression.TripleExpression;
import expression.exceptions.TripleParser;

public class ExpressionParser implements TripleParser {
    public ExpressionParser() {
    }

    @Override
    public TripleExpression parse(String expression) throws Exception {
        return new ExpressionParsing().parse(
                new LexicalSource(
                        new Lexical(expression).lexical_form()
                )
        );
    }
}
