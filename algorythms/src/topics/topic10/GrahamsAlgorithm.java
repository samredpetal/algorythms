package topics.topic10;


import collections.linkedList.VectorXY;
import java.util.*;


public class GrahamsAlgorithm {

    public static void main(String[] args) {
        List<VectorXY<Integer>> points = Arrays.asList(
                new VectorXY<>(3, 9),
                new VectorXY<>(5, 2),
                new VectorXY<>(-1, -4),
                new VectorXY<>(8, 3),
                new VectorXY<>(-6, 90),
                new VectorXY<>(23, 3),
                new VectorXY<>(4, -11)
        );

        List<VectorXY<Integer>> convexHull = GrahamsAlgorithm.getConvexHull(points);

        for (VectorXY<Integer> point : convexHull) {
            System.out.println(point);
        }
    }

    private static final int CLOCKWISE = 1;
    private static final int COUNTER_CLOCKWISE = 2;
    private static final int COLLINEAR = 3;
    
    /**
     * Проверяет лежат ли переданные точки на одной прямой
     */
    private static boolean areAllCollinear(List<VectorXY<Integer>> points) {

        if(points.size() < 2) {
            return true;
        }

        final VectorXY<Integer> a = points.get(0);
        final VectorXY<Integer> b = points.get(1);

        for(int i = 2; i < points.size(); i++) {

            VectorXY<Integer> c = points.get(i);

            if(getTurn(a, b, c) != COLLINEAR) {
                return false;
            }
        }

        return true;
    }


    /**
     * Возвращает выпуклую оболочку, созданную на основе переданных точек x и y
     */
    public static List<VectorXY<Integer>> getConvexHull(List<VectorXY<Integer>> points){

        List<VectorXY<Integer>> sorted = new ArrayList<>(getSortedVectorXYSet(points));

        if(sorted.size() < 3) {
            System.out.println("Недостаточно точек для создания выпуклой оболочки");
        }

        if(areAllCollinear(sorted)) {
            System.out.println("Невозможно создать выпуклую оболочку из точек лежащих на одной прямой");
        }

        Stack<VectorXY<Integer>> stack = new Stack<>();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {

            VectorXY<Integer> head = sorted.get(i);
            VectorXY<Integer> middle = stack.pop();
            VectorXY<Integer> tail = stack.peek();

            int turn = getTurn(tail, middle, head);

            switch(turn) {
                case COUNTER_CLOCKWISE:
                    stack.push(middle);
                    stack.push(head);
                    break;
                case CLOCKWISE:
                    i--;
                    break;
                case COLLINEAR:
                    stack.push(head);
                    break;
            }
        }

        // Закрыть оболочку
        stack.push(sorted.get(0));

        return new ArrayList<>(stack);
    }

    /**
     * Возвращает точки с наименьшим значением y. Если таких точек больше чем 1 возвращает одну из них
     */
    private static VectorXY<Integer> getLowestVectorXY(List<VectorXY<Integer>> points) {

        VectorXY<Integer> lowest = points.get(0);

        for(int i = 1; i < points.size(); i++) {

            VectorXY<Integer> temp = points.get(i);

            if(temp.y < lowest.y || (temp.y == lowest.y && temp.x < lowest.x)) {
                lowest = temp;
            }
        }

        return lowest;
    }

    /**
     * Возвращает отсортированное множество точек из переданного листа.
     * Точки отсортированы по возрастанию угла.
     */
    private static Set<VectorXY<Integer>> getSortedVectorXYSet(List<VectorXY<Integer>> points) {

        final VectorXY<Integer> lowest = getLowestVectorXY(points);

        TreeSet<VectorXY<Integer>> set = new TreeSet<>((a, b) -> {

            if(a == b || a.equals(b)) {
                return 0;
            }

            // use longs to guard against int-underflow
            double thetaA = Math.atan2((long)a.y - lowest.y, (long)a.x - lowest.x);
            double thetaB = Math.atan2((long)b.y - lowest.y, (long)b.x - lowest.x);

            if(thetaA < thetaB) {
                return -1;
            }
            else if(thetaA > thetaB) {
                return 1;
            }
            else {
                // collinear with the 'lowest' point, let the point closest to it come first

                // use longs to guard against int-over/underflow
                double distanceA = Math.sqrt((((long)lowest.x - a.x) * ((long)lowest.x - a.x)) +
                        (((long)lowest.y - a.y) * ((long)lowest.y - a.y)));
                double distanceB = Math.sqrt((((long)lowest.x - b.x) * ((long)lowest.x - b.x)) +
                        (((long)lowest.y - b.y) * ((long)lowest.y - b.y)));

                if(distanceA < distanceB) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        });

        set.addAll(points);

        return set;
    }

    /**
     * Возвращает направление полученную на основе алгоритма Грехема
     * (b.x-a.x * c.y-a.y) - (b.y-a.y * c.x-a.x)
     *
     * Если значение меньше 0, направление по часовой стрелке, если
     * больше 0, напраление против часовой стрелки, в другом случае
     * они лежат на одной прямой.
     */
    private static int getTurn(VectorXY<Integer> a, VectorXY<Integer> b, VectorXY<Integer> c) {

        // use longs to guard against int-over/underflow
        long crossProduct = (((long)b.x - a.x) * ((long)c.y - a.y)) -
                (((long)b.y - a.y) * ((long)c.x - a.x));

        if(crossProduct > 0) {
            return COUNTER_CLOCKWISE;
        }
        else if(crossProduct < 0) {
            return CLOCKWISE;
        }
        else {
            return COLLINEAR;
        }
    }
}