package collections.queue;

public class Main {
    public static void main(String[] args) {
//        PriorityQueue<String> practice.collections.queue = new PriorityQueue<>();

        RoundQueue<String> queue = new RoundQueue<>(10);


        queue.enqueue( "A");
        queue.enqueue( "C");
        queue.enqueue( "B");
        queue.enqueue( "D");

        System.out.println(queue.front());
        System.out.println(queue.dequeue());
        System.out.println(queue.front());
        System.out.println(queue.isEmpty());
        queue.enqueue("new");
        System.out.println(queue.front());
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
        queue.enqueue("sadf");
//        System.out.println(practice.collections.queue.dequeue());
//        System.out.println(practice.collections.queue.dequeue());
//        System.out.println(practice.collections.queue.dequeue());
//        System.out.println(practice.collections.queue.dequeue());
//        System.out.println(practice.collections.queue.dequeue());
//        System.out.println(practice.collections.queue.minElement());
//        System.out.println(practice.collections.queue.minKey());
//        System.out.println(practice.collections.queue.removeMin());
//        System.out.println(practice.collections.queue.size());
//        System.out.println(practice.collections.queue.removeMin());
//        System.out.println(practice.collections.queue.removeMin());
//        System.out.println(practice.collections.queue.removeMin());
//        System.out.println(practice.collections.queue.isEmpty());
//        System.out.println(practice.collections.queue.removeMin());
    }
}
