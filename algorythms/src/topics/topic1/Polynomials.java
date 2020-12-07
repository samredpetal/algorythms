package topics.topic1;

import collections.linkedList.LinkedList;
import collections.linkedList.Node;
import collections.linkedList.VectorXY;
import java.util.Scanner;

public class Polynomials {

    public static void main(String[] args) {
        System.out.println("Сложение многочленов:");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите многочлен A:");
        String polyA = scanner.nextLine();

        System.out.println("Введите многочлен B:");

        String polyB = scanner.nextLine();
        System.out.println(polyB);

        LinkedList<VectorXY<Integer>> A = new LinkedList<>();
        LinkedList<VectorXY<Integer>> B = new LinkedList<>();

        for (int[] a : parseExpression(polyA)) {
            A.add(new VectorXY<>(a[0], a[1]));
        }
        for (int[] a : parseExpression(polyB)) {
            B.add(new VectorXY<>(a[0], a[1]));
        }

        LinkedList<VectorXY<Integer>> C = sumPolynomials(A, B);

        for (VectorXY vector: C) {
            System.out.print(vector.x + "");
            if (!vector.y.equals(0)) {
                System.out.print("x^");
                System.out.print(vector.y + "");
            }
//            System.out.println();
        }
        System.out.println();
//7x^3-x^2+x-10
//8x^3-6x+3

    }

    static LinkedList<VectorXY<Integer>> sumPolynomials(LinkedList<VectorXY<Integer>> A, LinkedList<VectorXY<Integer>> B) {
        if (B.size() > A.size()) {
            LinkedList<VectorXY<Integer>> temp;
            temp = B;
            B = A;
            A = temp;
        }

        LinkedList<VectorXY<Integer>> C = new LinkedList<>();

        Node<VectorXY<Integer>> currentNodeA = A.getNode(0);
        Node<VectorXY<Integer>> currentNodeB = B.getNode(0);
        while (currentNodeA != null) {
            if (currentNodeA.getData().y.equals(currentNodeB.getData().y)) {
                C.add(new VectorXY<>(currentNodeA.getData().x + currentNodeB.getData().x, currentNodeA.getData().y));
                currentNodeB = currentNodeB.getNextNode();
            }else{
                C.add(new VectorXY<>(currentNodeA.getData().x, currentNodeA.getData().y));
            }
            currentNodeA = currentNodeA.getNextNode();
        }
        return C;
    }

    static int[][] parseExpression(String string){
        String[] terms = string.split("(?=-|[+])");
        int [][] xys = new int[terms.length][2];
        int i = 0;
        for (String term : terms) {
            if(term.matches("^[+-]?[0-9]*x$")){
                term = term.replaceAll("x", "x^1");
            }
            String splTerms[] = term.split("[x^]|x");
            int xy[] = new int[2];
            int counter = 0;
            for (String splTerm : splTerms) {
                if (splTerm.isEmpty()) {
                    continue;
                }
                if (!splTerm.matches("[-+]?[0-9]+")) {
                    if(splTerm.contains("+")){
                        splTerm = splTerm.replace("+", "+1");
                    }else{
                        splTerm = splTerm.replace("-", "-1");
                    }
                    xy[counter] = Integer.parseInt(splTerm);
                    counter++;
                }else{
                    xy[counter] = Integer.parseInt(splTerm);
                    counter++;
                }
            }
            xys[i] = xy;
            i++;
        }
        return xys;
    }

}


