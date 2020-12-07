package topics.topic8.binomialHeap;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Binomial Heap Test");

        /* Make object of BinomialHeap */
        BinomialHeap bh = new BinomialHeap();

        bh.insert(12);
        bh.insert(1);
        bh.insert(6);
        bh.delete(12);
        bh.displayHeap();

        BinomialHeap bh2 = new BinomialHeap();
        bh2.insert(23);
        bh2.insert(93);
        bh2.insert(94);

        System.out.println("\n===========================");
        System.out.print("Second heap:");
        bh2.displayHeap();

        System.out.println("\n===========================");
        System.out.println("After union:");
        bh.unionNodes(bh2.getNode());

        bh.displayHeap();



    }
}
