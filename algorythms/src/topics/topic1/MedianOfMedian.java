package topics.topic1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianOfMedian {
    /**
     * Получаем массив случайных чисел
     */
    private static ArrayList<Integer> getRandomArray(int size) {
        ArrayList<Integer> array = new ArrayList<Integer>();

        for(int i = 0; i < size; i++)
            array.add(i);

        return array;
    }

    /**
     * BFRT алгоритм
     * @param a - лист, в котором необходимо найти значение индекса
     * @param k - индекс, значение которого нужно найти
     * @return int - значение индекса k в массиве а
     */
    private static int findKthElement(List<Integer> a, int k) {
        if (a.size() < 10) {
            Collections.sort(a);
            return a.get(k);
        }

        ArrayList<Integer> medians = new ArrayList<Integer>();

        for (int i = 0; i < a.size() - a.size() % 5; i = i + 5)
            medians.add(getMedian(a.subList(i, i + 5)));

        int v = getMedian(medians);

        ArrayList<Integer> left = getPartition(a, v, true);
        ArrayList<Integer> right = getPartition(a, v, false);

        return (left.size() + 1 == k) ? v : (left.size() > k) ?
                findKthElement(left, k) : findKthElement(right, k - left.size());
    }

    /**
     * Получаем медиану
     */
    private static int getMedian(List<Integer> a) {
        Collections.sort(a);
        return a.get(a.size() / 2);
    }

    /**
     * Разбиваем массив на группы
     */
    private static ArrayList<Integer> getPartition(List<Integer> a, int v,
                                                   boolean isLessThan) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int val : a)
            if (isLessThan && val < v)
                res.add(val);
            else if (!isLessThan && val >= v)
                res.add(val);
        return res;
    }

    /**
     * Тест
     */
    public static void main(String[] args) {
        ArrayList<Integer> a = getRandomArray(20);

        System.out.println(a);
        System.out.println(findKthElement(a, 7));
    }
}