package expression.exceptions;

import expression.TripleExpression;

public class Main {
    public static String CorrectWhitespace(int length) {
        return " ".repeat(Math.max(0, length));
    }
    public static void main(String[] args) throws Exception {
        TripleParser ps1 = new ExpressionParser();
        try {
            TripleExpression result = ps1.parse("1000000*x*x*x*x*x/(x-1)");
            int i = 0;
            System.out.println("x" + CorrectWhitespace(8 - Integer.toString(i).length()) + "f");
            for (i = 0; i < 11; i++) {
                try {
                    System.out.println(i + CorrectWhitespace(8 - Integer.toString(i).length()) + result.evaluate(i, 0, 0));
                } catch (Exception e) {
                    System.out.println(i + CorrectWhitespace(8 - Integer.toString(i).length()) + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Oops " + e.getMessage());
        }

    }
}
