package expression.generic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mode1;
        String expr;
        try {
            mode1 = scanner.next().substring(1);
            expr = scanner.next();
            GenericTabulator tree = new GenericTabulator();
            Object[][][] treemap = tree.tabulate(mode1, expr, -2, 2, -2, 2, -2, 2);
            System.out.println(mode1);
            System.out.println(expr);
            for (int i = 0; i < treemap.length; i++) {
                for (int j = 0; j < treemap[i].length; j++) {
                    for (int k = 0; k < treemap[i][j].length; k++) {
                        System.out.println("Ur expression with arguments by x " + (-2 + i) + " by y " + (-2 + j) + " by z " + (-2 + k) + " have result " + treemap[i][j][k]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Doesn't have this argument:< "  + e.getMessage());
        }

    }
}
