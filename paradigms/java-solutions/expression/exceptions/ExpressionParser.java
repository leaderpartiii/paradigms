package expression.exceptions;


import expression.ListExpression;
import expression.TripleExpression;
import expression.parser.ExpressionParsing;
import expression.parser.Lexical;
import expression.parser.LexicalSource;

import java.util.List;

public class ExpressionParser implements TripleParser, ListParser {
    public ExpressionParser() {
    }
    @Override
    public TripleExpression parse(String expression) throws Exception {
        return new ExpressionParsing().parse(new LexicalSource(new Lexical(expression).lexical_form()));
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) throws Exception {
        return new ExpressionParsing().parse(new LexicalSource(new Lexical(expression, variables).lexical_form()));
    }
}
