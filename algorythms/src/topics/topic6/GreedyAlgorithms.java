package topics.topic6;

import java.util.ArrayList;
import java.util.Arrays;

public class GreedyAlgorithms {

    public static void main(String[] args) {
//        new GreedyAlgorithms().activitySelectionProblem();
        new GreedyAlgorithms().knapsackProblem();
    }

    private void activitySelectionProblem() {
        int activities[][] = new int[][]{{9, 10}, {1, 5}, {4, 8}, {13, 15}, {2, 4}, {7, 14}, {5, 12}, {3, 10}};
        ArrayList<int[]> result = new ArrayList<>();
        int resCounter = 0;

        System.out.println("Activities:");
        for(int m[]: activities)
            System.out.print(Arrays.toString(m));
        System.out.println();


        /* sorting array */
        for(int i = 0; i < activities.length - 1; i++) {
            for(int j = 0; j < activities.length - 1; j++) {
                if (activities[j][1] > activities[j + 1][1]) {
                    int temp[] = activities[j+1];
                    activities[j+1] = activities[j];
                    activities[j] = temp;
                } else if (activities[j][1] == activities[j + 1][1]) {
                    if (activities[j][0] > activities[j + 1][0]) {
                        int temp[] = activities[j+1];
                        activities[j+1] = activities[j];
                        activities[j] = temp;
                    }
                }
            }
        }

        //
        /* print sorted array */
//        for(int m[]: activities)
//            System.out.print(Arrays.toString(m));
//        System.out.println();

        /* put first element */
        result.add(activities[0]);

        for(int i = 1; i < activities.length; i++) {
            if (activities[i][0] >= result.get(resCounter)[1]) {
                result.add(activities[i]);
                resCounter++;
            }
        }

        System.out.println("Result");
        /* print result array */
        for(int m[]: result)
            System.out.print(Arrays.toString(m));

    }

    private void knapsackProblem() {
        int val[] = new int[]{50, 100, 130};
        int wt[] = new int[]{10, 20, 40};

        int W = 50;
        int i, w;
        int rv[][] = new int[val.length+1][W+1];

        for (i = 0; i <= val.length; i++)
        {
            for (w = 0; w <= W; w++)
            {
                if (i==0 || w==0)
                    rv[i][w] = 0;
                else if (wt[i-1] <= w)
                    rv[i][w] = Math.max(val[i-1] + rv[i-1][w-wt[i-1]], rv[i-1][w]);
                else
                    rv[i][w] = rv[i-1][w];
            }
        }

        System.out.println(rv[val.length][W]);
    }
}
