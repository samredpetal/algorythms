package collections.linkedList;

public class Node<E> {
    private Node nextNode;
    private E data;

    Node(E data) {
        this.data = data;
    }

    public Node getNextNode() {
        return this.nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public E getData() {
        return this.data;
    }

    public boolean hasNext() {
        return nextNode != null;
    }
}
