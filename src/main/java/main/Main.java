package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the number :");
            int i = scanner.nextInt();
            int res = myMethod(i);
            System.out.println(res);
        }
    }

    public static int myMethod(int n) {
        return 0;
    }
}
