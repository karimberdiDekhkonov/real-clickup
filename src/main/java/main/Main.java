package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the number :");
            int i = scanner.nextInt();
            boolean res = myMethod(i);
            System.out.println(res);
        }
    }

    public static boolean myMethod(int n) {
        long sum =1;
        for (int i = 2; i <= n/2; i++) {
            if (n%i==0){
                sum+=i;
            }
        }
        return sum==n;
    }
}
