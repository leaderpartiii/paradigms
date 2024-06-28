package expression.parser;

import expression.*;
import expression.exceptions.*;

import static expression.parser.LexemaType.eol;
import static expression.parser.Lexical.isCloseBracket;
import static expression.parser.Lexical.isOpenBracket;

public class ExpressionParsing {
    public ExpressionParsing() {
    }
//    member : lowPriority * eol ;
//
//    lowPriority : midPriority ( ( '+' | '-' ) midPriority )* ;
//
//    lowPriority : body ( ( '*' | '/' ) body )* ;
//
//    body : NUMBER | '(' member ')' ;


//    member : or* EOF ;
//
//    or : xor ( ( ' | ' ) xor ) ;
//
//    xor : and ( ( ' ^ ' ) and )* ;
//
//    and : lowPriority ( ( ' & ' ) lowPriority )* ;
//
//    lowPriority: midPriority ( ( '+' | '-' ) midPriority )* ;
//
//    midPriority : body ( ( '*' | '/' ) body )* ;
//
//    body : not | unary | NUMBER | '(' member ')' ;
//
//    unary : -body ;
//
//    not : ~body;
    public General parse(LexicalSource source) throws LexicalException {
        General mem =  member(source);
        if ((source.next().getLex())!=eol) {
            throw new LexicalException("right brackets");
        } else {
            return mem;
        }
    }

    // :NOTE: не public
    public static General member(LexicalSource lexicalSource) throws LexicalException {
        Lexema one_object = lexicalSource.next();
        if (one_object.getLex() == eol) {
            throw new LexicalException("not empty form");
        } else {
            lexicalSource.back();
            return or(lexicalSource);
        }
    }

    //            or : xor ( ( ' | ' ) xor ) ;
    // :NOTE: я вас умоляю, private
    public static General or(LexicalSource lexicalSource) throws LexicalException {
        General value = xor(lexicalSource);
        while (true) {
            Lexema one_object = lexicalSource.next();
            if (one_object.getLex() == LexemaType.or) {
                value = new BinOr(value, xor(lexicalSource));
            } else {
                lexicalSource.back();
                return value;
            }
        }
    }

    //            xor : and ( ( ' ^ ' ) and )* ;
    public static General xor(LexicalSource lexicalSource) throws LexicalException {
        General value = and(lexicalSource);
        while (true) {
            Lexema one_object = lexicalSource.next();
            if (one_object.getLex() == LexemaType.xor) {
                value = new BinXor(value, and(lexicalSource));
            } else {
                lexicalSource.back();
                return value;
            }
        }
    }

    //            and : lowPriority ( ( ' & ' ) lowPriority )* ;
    public static General and(LexicalSource lexicalSource) throws LexicalException {
        General value = lowPriority(lexicalSource);
        while (true) {
            Lexema one_object = lexicalSource.next();
            if (one_object.getLex() == LexemaType.and) {
                value = new BinAnd(value, lowPriority(lexicalSource));
            } else {
                lexicalSource.back();
                return value;
            }
        }
    }


    //        lowPriority: midPriority ( ( '+' | '-' ) midPriority )* | midPriority -NUMBER;
    public static General lowPriority(LexicalSource lexicalSource) throws LexicalException {
        General value = midPriority(lexicalSource);
        while (true) {
            Lexema oneObject = lexicalSource.next();
            if (oneObject.getLex() == LexemaType.minus) {
                value = new CheckedSubtract(value, midPriority(lexicalSource));
            } else if (oneObject.getLex() == LexemaType.plus) {
                value = new CheckedAdd(value, midPriority(lexicalSource));
            } else if ((oneObject.getLex() == LexemaType.number) || (oneObject.getLex() == LexemaType.var)) {
                throw new LexicalException("right form numbers");
            } else {
                lexicalSource.back();
                return value;
            }
        }
    }

    //            midPriority : body  ( ( '*' | '/' ) body )* ;
    public static General midPriority(LexicalSource lexicalSource) throws LexicalException {
        General value = body(lexicalSource);
        while (true) {
            Lexema one_object = lexicalSource.next();
            if (one_object.getLex() == LexemaType.divide) {
                value = new CheckedDivide(value, body(lexicalSource));
            } else if (one_object.getLex() == LexemaType.multiply) {
                value = new CheckedMultiply(value, body(lexicalSource));
            } else {
                lexicalSource.back();
                return value;
            }
        }
    }

    //        body : not | unary | pow2 | log2 | NUMBER | '(' member ')'  ;
    public static General body(LexicalSource lexicalSource) throws LexicalException {
        Lexema one_object = lexicalSource.next();
        if (one_object.getLex() == LexemaType.minus) {
            General value = body(lexicalSource);
            return new CheckedNegate(value);
        } else if (one_object.getLex() == LexemaType.not) {
            General value = body(lexicalSource);
            return new UnaryNot(value);
        } else if (one_object.getLex() == LexemaType.pow2) {
            General value = body(lexicalSource);
            return new Pow2(value);
        } else if (one_object.getLex() == LexemaType.log2) {
            General value = body(lexicalSource);
            return new Log2(value);
        } else if (one_object.getLex() == LexemaType.number) {
            return new CheckedConst(one_object.getValue());
        } else if (one_object.getLex() == LexemaType.var) {
            if (one_object.getValue() != null) {
                return new CheckedVariable(one_object.getValue());
            } else {
                return new CheckedVariable(one_object.getIndex());
            }
        } else if (isOpenBracket(one_object.getLex()) != LexemaType.clear) {
            LexemaType secondBracket = isOpenBracket(one_object.getLex());
            General value = member(lexicalSource);
            one_object = lexicalSource.next();
            if (one_object.getLex() != secondBracket) {
                throw new LexicalException("right brackets");
            }
            return value;
        } else if (isCloseBracket(one_object.getLex())) {
            throw new LexicalException("right brackets");
        } else {
            throw new LexicalException("right form not in " + lexicalSource);
        }
    }
//   unary : - body ;
//    not  : ~ body
//    pow2 : pow2 body;
//    log2 : log2 body;
}
