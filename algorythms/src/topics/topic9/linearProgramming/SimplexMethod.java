package topics.topic9.linearProgramming;


import java.util.Arrays;

/**
 * Решение проблемы линейного программирования по симплексному методу
 */
public class SimplexMethod {

    public static void main(String[] args) {
        SimplexMethod simplexMethod = new SimplexMethod(2, 5);
        simplexMethod.fillTable(new float[][]{{20, 6}, {4, 1}, {2, 1}, {1,0}, {0,1}});


        int error = NOT_OPTIMAL;

        while (error != IS_OPTIMAL && error != UNBOUNDED) {
            error = simplexMethod.compute();
        }



        for (float[] f : simplexMethod.getTable()) {
            System.out.println(Arrays.toString(f));
        }
    }


    /**
     * Строки и столбцы
     */
    private int rows, cols;

    /**
     * Симплекс таблица
     */
    private float[][] table;
    private boolean solutionIsUnbounded = false;

    /**
     * Типы ошибок
     */
    private static final int NOT_OPTIMAL = 0;
    private static final int IS_OPTIMAL = 1;
    private static final int UNBOUNDED = 2;


    /**
     * Инициализация
     */
    public SimplexMethod(int numOfConstraints, int numOfUnknowns) {
        /* Кол-во строк + 1 */
        rows = numOfConstraints + 1;

        /* Кол-во столбцов + 1 */
        cols = numOfUnknowns + 1;
        table = new float[rows][];

        for (int i = 0; i < rows; i++) {
            table[i] = new float[cols];
        }
    }

    /**
     * Вывод симплекс таблицы
     */
    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String value = String.format("%.2f", table[i][j]);
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Заполнение сиплекс таблицы с коэфициентами
     */
    public void fillTable(float[][] data) {
        for (int i = 0; i < table.length; i++) {
            System.arraycopy(data[i], 0, this.table[i], 0, data[i].length);
        }
    }

    /**
     * Вычисление значений сиплекс таблицы
     * Вычисляется пока не найдено оптимальное решение
     */
    public int compute() {
        // Шаг 1
        if (checkOptimality()) {
            return IS_OPTIMAL;
        }

        // Шаг 2
        int pivotColumn = findEnteringColumn();
        System.out.println("Pivot Column: " + pivotColumn);

        // Шаг 3
        float[] ratios = calculateRatios(pivotColumn);
        if (solutionIsUnbounded)
            return UNBOUNDED;
        int pivotRow = findSmallestValue(ratios);

        // Шаг 4
        formNextTableau(pivotRow, pivotColumn);

        /*
         * Вернуть ошибку неотпимальный так как мы сформирвали новую таблицу
         */
        return NOT_OPTIMAL;
    }

    /**
     * Новая таблица с ранее вычисленными значениями
     */
    private void formNextTableau(int pivotRow, int pivotColumn) {
        float pivotValue = table[pivotRow][pivotColumn];
        float[] pivotRowVals = new float[cols];
        float[] pivotColumnVals = new float[cols];
        float[] rowNew = new float[cols];

        System.arraycopy(table[pivotRow], 0, pivotRowVals, 0, cols);

        for (int i = 0; i < rows; i++)
            pivotColumnVals[i] = table[i][pivotColumn];

        for (int i = 0; i < cols; i++)
            rowNew[i] = pivotRowVals[i] / pivotValue;

        for (int i = 0; i < rows; i++) {
            if (i != pivotRow) {
                for (int j = 0; j < cols; j++) {
                    float c = pivotColumnVals[i];
                    table[i][j] = table[i][j] - (c * rowNew[j]);
                }
            }
        }

        /* Заменить строку */
        System.arraycopy(rowNew, 0, table[pivotRow], 0, rowNew.length);
    }

    /**
     * Вычисление пропорций пивот строки
     */
    private float[] calculateRatios(int column) {
        float[] positiveEntries = new float[rows];
        float[] res = new float[rows];
        int allNegativeCount = 0;
        for (int i = 0; i < rows; i++) {
            if (table[i][column] > 0) {
                positiveEntries[i] = table[i][column];
            } else {
                positiveEntries[i] = 0;
                allNegativeCount++;
            }
        }

        if (allNegativeCount == rows)
            this.solutionIsUnbounded = true;
        else {
            for (int i = 0; i < rows; i++) {
                float val = positiveEntries[i];
                if (val > 0) {
                    res[i] = table[i][cols - 1] / val;
                }
            }
        }

        return res;
    }

    private int findEnteringColumn() {
        float[] values = new float[cols];
        int location = 0;

        int pos, count = 0;
        for (pos = 0; pos < cols - 1; pos++) {
            if (table[rows - 1][pos] < 0) {
                count++;
            }
        }

        if (count > 1) {
            for (int i = 0; i < cols - 1; i++)
                values[i] = Math.abs(table[rows - 1][i]);
            location = findLargestValue(values);
        } else location = count - 1;

        return location;
    }


    /**
     * Найти наименьшее значение в массиве
     */
    private int findSmallestValue(float[] data) {
        float minimum;
        int c, location = 0;
        minimum = data[0];

        for (c = 1; c < data.length; c++) {
            if (data[c] > 0) {
                if (Float.compare(data[c], minimum) < 0) {
                    minimum = data[c];
                    location = c;
                }
            }
        }

        return location;
    }

    /**
     * Найти наибольшее значение в массиве
     */
    private int findLargestValue(float[] data) {
        float maximum = 0;
        int c, location = 0;
        maximum = data[0];

        for (c = 1; c < data.length; c++) {
            if (Float.compare(data[c], maximum) > 0) {
                maximum = data[c];
                location = c;
            }
        }

        return location;
    }

    /**
     * Проверить является ли таблица отпимальной
     */
    public boolean checkOptimality() {
        boolean isOptimal = false;
        int vCount = 0;

        for (int i = 0; i < cols - 1; i++) {
            float val = table[rows - 1][i];
            if (val >= 0) {
                vCount++;
            }
        }

        if (vCount == cols - 1) {
            isOptimal = true;
        }

        return isOptimal;
    }

    /**
     * Возвращает сиплекс таблицу
     */
    public float[][] getTable() {
        return table;
    }
}