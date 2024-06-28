package expression.parser;

import expression.*;
import expression.exceptions.TripleParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            TripleParser ps = new ExpressionParser();
            TripleExpression result = ps.parse("-2147483648-2147483648");
            Scanner sc = new Scanner(System.in);
            try {
                int x = sc.nextInt();
                int y = sc.nextInt();
                int z = sc.nextInt();
                int res = result.evaluate(x, y, z);
                System.out.println(res);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Что-то пошло не так" + e.getMessage());
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
