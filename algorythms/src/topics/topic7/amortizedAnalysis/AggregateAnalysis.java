package topics.topic7.amortizedAnalysis;

import collections.stack.Stack;


public class AggregateAnalysis {

    public static void main(String[] args) {
//        testMultiPop();
        testBinaryCounter();
    }

    void multiPop(Stack stack, int k) {
        while (!stack.isEmpty() && k > 0) {
            stack.pop();
            k--;
        }
    }

    static void testMultiPop() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        for (int i : stack) {
            System.out.print(i + "\t");
        }
        System.out.println();

        new AggregateAnalysis().multiPop(stack, 4);

        for (int i : stack) {
            System.out.print(i + "\t");
        }
        System.out.println();

    }

    static void testBinaryCounter() {
        int k = 5;
        int array[] = new int[k];

        new AggregateAnalysis().incrementAndPrintBinaryCounter(array);
        new AggregateAnalysis().incrementAndPrintBinaryCounter(array);
        new AggregateAnalysis().incrementAndPrintBinaryCounter(array);
        new AggregateAnalysis().incrementAndPrintBinaryCounter(array);

    }

    void printBinaryCounter(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            System.out.print(array[i]);
        }
        System.out.println();
    }

    void incrementAndPrintBinaryCounter(int[] array) {
        System.out.println("initial state: ");
        new AggregateAnalysis().printBinaryCounter(array);
        int cost = new AggregateAnalysis().incrementBinaryCounter(array);

        System.out.println("final state: ");
        new AggregateAnalysis().printBinaryCounter(array);
        System.out.println("cost: " + cost);
        System.out.println();
    }

    int incrementBinaryCounter(int[] array) {
        int i = 0;
        int cost = 0;
        while (i < array.length && array[i] == 1) {
            array[i] = 0;
            i++;
            cost++;
        }

        if (i < array.length) {
            array[i] = 1;
            cost++;
        }
        return cost;
    }


}
