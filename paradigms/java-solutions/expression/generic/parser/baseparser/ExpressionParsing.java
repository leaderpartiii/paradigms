package expression.generic.parser.baseparser;

import expression.exceptions.LexicalException;
import expression.generic.parser.*;
import expression.generic.types.AbstractType;

import static expression.generic.parser.baseparser.LexemaType.*;
import static expression.generic.parser.baseparser.Lexical.isCloseBracket;
import static expression.generic.parser.baseparser.Lexical.isOpenBracket;

public class ExpressionParsing<T> {
    AbstractType<T> type;

    public ExpressionParsing(AbstractType<T> type) {
        this.type = type;
    }

//    member : lowPriority * EOL ;
//
//    lowPriority : midPriority ( ( '+' | '-' ) midPriority )* ;
//
//    MidPriority : body ( ( '*' | '/' ) body )* ;
//
//    body : NUMBER | '(' member ')' ;

    public GeneralExpression<T> parse(LexicalSource<T> source) throws LexicalException {
        GeneralExpression<T> mem = member(source);
        if ((source.next().getLex()) != EOL) {
            throw new LexicalException("right brackets");
        } else {
            return mem;
        }
    }

    //    member : lowPriority * eol ;
    private GeneralExpression<T> member(LexicalSource<T> lexicalSource) throws LexicalException {
        Wrapper<T> oneObject = lexicalSource.next();
        if (oneObject.getLex() == EOL) {
            throw new LexicalException("not empty form");
        } else {
            lexicalSource.back();
            return priority(lexicalSource);
        }
    }

    // :NOTE: copy-paste
    private GeneralExpression<T> priority(LexicalSource<T> lexicalSource) throws LexicalException {
        GeneralExpression<T> value = body(lexicalSource);
        while (true) {
            Wrapper<T> oneObject = lexicalSource.next();
            if (oneObject.getLex() == DIVIDE) {
                value = new Divide<>(value, priority(lexicalSource), type);
            } else if (oneObject.getLex() == MULTIPLY) {
                value = new Multiply<>(value, priority(lexicalSource), type);
            } else if (oneObject.getLex() == MINUS) {
                value = new Subtract<>(value, priority(lexicalSource), type);
            } else if (oneObject.getLex() == PLUS) {
                value = new Add<>(value, priority(lexicalSource), type);
            } else {
                lexicalSource.back();
                return value;
            }
        }
    }

//    body : -body | NUMBER | '(' member ')' ;
    private GeneralExpression<T> body(LexicalSource<T> lexicalSource) throws LexicalException {
        Wrapper<T> oneObject = lexicalSource.next();
        if (oneObject.getLex() == MINUS) {
            GeneralExpression<T> value = body(lexicalSource);
            return new Negate<>(value, type);
        } else if (oneObject.getLex() == NUMBER) {
            return new Const<>(oneObject.getValue(), type);
        } else if (oneObject.getLex() == VAR) {
            if (oneObject.getInteger() != -1) {
                return new Variable<>(oneObject.getInteger(), type);
            }
        } else if (isOpenBracket(oneObject.getLex()) != CLEAR) {
            LexemaType secondBracket = isOpenBracket(oneObject.getLex());
            GeneralExpression<T> value = member(lexicalSource);
            oneObject = lexicalSource.next();
            if (oneObject.getLex() != secondBracket) {
                throw new LexicalException("right brackets");
            }
            return value;
        } else if (isCloseBracket(oneObject.getLex())) {
            throw new LexicalException("right brackets");
        }
        throw new LexicalException("right form not in " + lexicalSource);
    }
}
