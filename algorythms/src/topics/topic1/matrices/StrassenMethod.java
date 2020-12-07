package topics.topic1.matrices;

import java.util.Scanner;

public class StrassenMethod {

    public static void main (String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Умножение матрицы по алгоритму Штрассена:\n");

        StrassenMethod s = new StrassenMethod();

        System.out.println("Размер матрицы:");
        int N = scan.nextInt();

        System.out.println("Матрица X:");
        int[][] X = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                X[i][j] = scan.nextInt();

        System.out.println("Матрица Y:");
        int[][] Y = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                Y[i][j] = scan.nextInt();

        int[][] XY = s.multiply(X, Y);

        System.out.println("\nПроизведение матриц X и Y:");
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.print(XY[i][j] +" ");
            System.out.println();
        }

    }

    /**
     * Функция для умножения матрицы по штрассену
     */
    public int[][] multiply(int[][] X, int[][] Y)
    {
        int n = X.length;
        int[][] R = new int[n][n];

        /** случай когда матрица уже не делится **/
        if (n == 1)
            R[0][0] = X[0][0] * Y[0][0];
        else
        {
            int[][] X11 = new int[n/2][n/2];
            int[][] X12 = new int[n/2][n/2];
            int[][] X21 = new int[n/2][n/2];
            int[][] X22 = new int[n/2][n/2];

            int[][] Y11 = new int[n/2][n/2];
            int[][] Y12 = new int[n/2][n/2];
            int[][] Y21 = new int[n/2][n/2];
            int[][] Y22 = new int[n/2][n/2];

            /** Деление матрицы A на четыре **/
            split(X, X11, 0 , 0);
            split(X, X12, 0 , n/2);
            split(X, X21, n/2, 0);
            split(X, X22, n/2, n/2);

            /** Деление матрицы B на четыре **/
            split(Y, Y11, 0 , 0);
            split(Y, Y12, 0 , n/2);
            split(Y, Y21, n/2, 0);
            split(Y, Y22, n/2, n/2);

            /**
             P1 = X11 (Y12 - Y22)
             P2 = (X11 + X12) Y22
             P3 = (X21 + X22) Y11
             P4 = X22 (Y21 - Y11)
             P5 = (X11 + X22)(Y11 + Y22)
             P6 = (X12 - X22) (Y21 + Y22)
             P7 = (X11 - X21) (Y11 + Y12)
             **/

            int [][] P1 = multiply(X11, minus(Y12, Y22));
            int [][] P2 = multiply(plus(X11, X12), Y22);
            int [][] P3 = multiply(plus(X21, X22), Y11);
            int [][] P4 = multiply(X22, minus(Y21, Y11));
            int [][] P5 = multiply(plus(X11, X22), plus(Y11, Y22));
            int [][] P6 = multiply(minus(X12, X22), plus(Y21, Y22));
            int [][] P7 = multiply(minus(X11, X21), plus(Y11, Y12));

            /**
             C11 = P5 + P4 - P2 + P6
             C12 = P1 + P2
             C21 = P3 + P4
             C22 = P1 + P5 - P3 - P7
             **/
            int [][] C11 = plus(minus(plus(P5, P4), P2), P6);
            int [][] C12 = plus(P1, P2);
            int [][] C21 = plus(P3, P4);
            int [][] C22 = minus(minus(plus(P1, P5), P3), P7);

            /** Объединение 4 матриц в одну матрицу **/
            join(C11, R, 0 , 0);
            join(C12, R, 0 , n/2);
            join(C21, R, n/2, 0);
            join(C22, R, n/2, n/2);
        }
        /** Конечная матрица */
        return R;
    }

    /**
     * Вычитание матриц
     **/
    public int[][] minus(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    /**
     * Сложение двух матриц
     **/
    public int[][] plus(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    /**
     * Деление матрицы в дочерние
     **/
    public void split(int[][] P, int[][] C, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    /**
     * Объединение дочерних матриц в родительский
     **/
    public void join(int[][] C, int[][] P, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }

}
