package topics.topic1.DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;

public class DynamicProgramming {

    public static void main(String[] args){
        greatestSequence();
//        livensteinDistance();
    }


    public static void livensteinDistance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строки:");
//        String rowStr = scanner.nextLine();
        String rowStr = "algorithm";

        System.out.println("Введите столбцы:");
//        String columnStr = scanner.nextLine();
        String columnStr = "altruistik";
        rowStr = "+" + rowStr;
        columnStr = "-" + columnStr;


        int rn = rowStr.length();
        int cn = columnStr.length();

        int matrix[][] = new int[cn][rn];
        char row[] = rowStr.toCharArray();
        char column[] = columnStr.toCharArray();

        for(int i = 0; i < cn; i++) {
            matrix[i][0] = i;
        }
        for(int i = 0; i < rn; i++) {
            matrix[0][i] = i;
        }

        for(int i = 1; i < cn; i++) {
            for(int j = 1; j < rn; j++) {
                if (row[j] == column[i]) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    matrix[i][j] = Math.min(matrix[i-1][j] + 1, Math.min(matrix[i][j-1] + 1, matrix[i-1][j-1] + 1));
                }
            }
        }

        System.out.println(rowStr);
        int j = 0;
        for(int[] i: matrix) {
            System.out.print(column[j] + " ");
            System.out.println(Arrays.toString(i));
            j++;
        }
    }

    public static void greatestSequence() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строки:");
//        String rowStr = scanner.nextLine();

        System.out.println("Введите столбцы:");
//        String columnStr = scanner.nextLine();
        String rowStr = "algorithm";

        System.out.println("Введите столбцы:");
//        String columnStr = scanner.nextLine();
        String columnStr = "altruistik";
        columnStr = "c" + columnStr;
        rowStr = "r" + rowStr;

        String length = "";

        int rn = rowStr.length();
        int cn = columnStr.length();

        int matrix[][] = new int[rn][cn];
        char row[] = rowStr.toCharArray();
        char column[] = columnStr.toCharArray();

        for(int i = 0; i < rn; i++) {
            matrix[i][0] = 0;
        }
        for(int i = 0; i < rn; i++) {
            matrix[0][i] = 0;
        }

        for(int i = 1; i < rn; i++) {
            for(int j = 1; j < cn; j++) {
                matrix[i][j] = Math.max(matrix[i-1][j], matrix[i][j-1]);
                if (row[i] == column[j]) {
                    matrix[i][j] = matrix[i-1][j-1] + 1;
                    if (!length.contains(String.valueOf(row[i]))) {
                        length += row[i];
                    }
                }
            }
        }

        for(int[] i: matrix) {

            System.out.println(Arrays.toString(i));

        }
        System.out.println();
        System.out.println("Matrix length = " + length);


    }

}
