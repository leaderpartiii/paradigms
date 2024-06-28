package expression.generic.parser.baseparser;

import expression.exceptions.LexicalException;

public interface TripleExpression<T> {
    T evaluate(T a, T b, T c);
}
