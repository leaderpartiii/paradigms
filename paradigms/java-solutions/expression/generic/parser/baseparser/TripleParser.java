package expression.generic.parser.baseparser;

import expression.exceptions.LexicalException;

@FunctionalInterface
public interface TripleParser<T> {
    TripleExpression<T> parse(String expression) throws LexicalException;
}
