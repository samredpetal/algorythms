package topics.topic1.matrices;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Matrix [n][m] - enter n:");
        int n = scanner.nextInt();
        System.out.println("Matrix [n][m] - enter m:");
        int m = scanner.nextInt();
        int A[][] = new int[n][m];
        int B[][] = new int[m][n];

        System.out.println("Matrix A:");
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                A[i][j] = scanner.nextInt();
            }
        }
        System.out.println("Matrix B:");
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                B[i][j] = scanner.nextInt();
            }
        }

        int C[][] = simpleMethod(A, B);
//        int C[][] = strassen(A, B);

        System.out.println("Answer - Matrix C:");
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }

    }

    static int[][] simpleMethod(int A[][], int B[][]){
        int n = A.length;
        int m = A[0].length;
        int C[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                C[i][j] = 0;
                for(int k = 0; k < m; k++) {
                    C[i][j] += A[i][k]*B[k][j];
                }
            }
        }
        return C;
    }

    static int[][] strassen(int A[][], int B[][]) {
        int n = A.length;
        int C[][] = new int[n][n];

        int p1 = A[0][0]*(B[0][1] - B[1][1]);
        int p2 = (A[0][0] + A[0][1])* B[1][1];
        int p3 = (A[1][0] + A[1][1])* B[0][0];
        int p4 = A[1][1]*(B[1][0] - B[0][0]);
        int p5 = (A[0][0] + A[1][1])*(B[0][0] + B[1][1]);
        int p6 = (A[0][1] - A[1][1])*(B[1][0] + B[1][1]);
        int p7 = (A[0][0] - A[1][0])*(B[0][0] + B[0][1]);

        C[0][0] = p5 + p4 - p2 + p6;
        C[0][1] = p1 + p2;
        C[1][0] = p3 + p4;
        C[1][1] = p1 + p5 - p3 - p7;
        return C;
    }
}
