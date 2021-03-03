package com.example.hackerrank;

import java.util.Scanner;

/**
 * @author Taner YILDIRIM
 */
public class StairCase {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numInputs = scan.nextInt();
        scan.close();
        printTower(numInputs);
    }

    private static void printTower(int level) {
        for (int i = level; i > 0 ; i--) {
            for (int j = 1; j <= level; j++) {

                if(i <= j)
                    System.out.print("#");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
