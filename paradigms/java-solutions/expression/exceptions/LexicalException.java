package expression.exceptions;


public class LexicalException extends Exception  {
    public LexicalException(String e) {
        super("Incorrect formula, please enter new correct formula with " + e);
    }
}
