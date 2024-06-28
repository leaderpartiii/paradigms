package expression;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        General result = new Multiply(
                new Subtract(
                        new Variable("x"),
                        new Const(1)
                ),
                new Subtract(
                        new Variable("x"),
                        new Const(1)
                )
        );
        Scanner sc = new Scanner(System.in);
        try {
            int x = sc.nextInt();
            int res = result.evaluate(x);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("Что-то пошло не так" + e.getMessage());
        }
        sc.close();
//        Scanner sc = new Scanner(System.in);
//        System.out.println(sc.nextInt()+ sc.nextInt());
    }
}
