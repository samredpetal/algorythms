package topics.topic6;

import java.util.ArrayList;
import java.util.Arrays;

public class Matroids {
    public static void main(String[] args) {
        new Matroids().matroids();
    }

    public void matroids() {
        String tasks[] = new String[]{"1", "2", "3", "4", "5"};
        String timeIntervals[] = new String[tasks.length];
        int d[] = new int[]{2, 3, 2, 3, 1};
        int w[] = new int[]{10, 40, 50, 20, 30};


        System.out.println();
        System.out.print("Task:\t");
        for(int i = 0; i < tasks.length; i++) {
            System.out.print(tasks[i] + "\t");
        }
        System.out.println();
        System.out.print("Time:\t");
        for(int i = 0; i < tasks.length; i++) {
            System.out.print(d[i] + "\t");
        }
        System.out.println();
        System.out.print("Cost:\t");
        for(int i = 0; i < tasks.length; i++) {
            System.out.print(w[i] + "\t");
        }


        for(int i = 1; i < tasks.length; i++) {
            for (int j = 1; j < tasks.length; j++) {
                if (w[j - 1] < w[j]) {
                    int temp = w[j-1];
                    w[j - 1] = w[j];
                    w[j] = temp;

                    String tempS = tasks[j-1];
                    tasks[j - 1] = tasks[j];
                    tasks[j] = tempS;

                    temp = d[j-1];
                    d[j - 1] = d[j];
                    d[j] = temp;
                }
            }
        }

        System.out.println();
        System.out.print("Task:\t");
        for(int i = 0; i < tasks.length; i++) {
            System.out.print(tasks[i] + "\t");
        }
        System.out.println();
        System.out.print("Time:\t");
        for(int i = 0; i < tasks.length; i++) {
            System.out.print(d[i] + "\t");
        }
        System.out.println();
        System.out.print("Cost:\t");
        for(int i = 0; i < tasks.length; i++) {
            System.out.print(w[i] + "\t");
        }

        int profit = 0;
        int straff = 0;

        ArrayList<Integer> done = new ArrayList<>();

        for (int i = 0; i < tasks.length; i++) {
            if (done.contains(d[i])) {
                straff += w[i];
                timeIntervals[i] = "No";
            } else {
                profit += w[i];
                timeIntervals[i] = "Yes";
                done.add(d[i]);
            }
        }


        System.out.println();
        System.out.println(Arrays.toString(tasks));
        System.out.println(Arrays.toString(d));
        System.out.println(Arrays.toString(timeIntervals));
        System.out.println(Arrays.toString(w));

        System.out.println();
        System.out.println("Profit: " + profit);
        System.out.println("Straff: " + straff);
    }
}
