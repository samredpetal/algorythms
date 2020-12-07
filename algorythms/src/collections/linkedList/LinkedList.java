package collections.linkedList;

import java.util.Iterator;

public class LinkedList<E> implements Iterable<E> {
    private Node head;
    private int size = -1;


    public void add(E data) {
        this.size++;
        if (head == null) {
            head = new Node(data);
            return;
        }
        Node current = head;
        while (current.getNextNode() != null) {
            current = current.getNextNode();
        }
        current.setNextNode(new Node(data));
    }

    public void addToStart(String data) {
        Node newHead = new Node(data);
        newHead.setNextNode(head);
        this.head = newHead;
        this.size++;
    }

    public void addTo(String data, int index) {
        if (index > size + 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node newNode = new Node(data);
        if (index == 0) {
            newNode.setNextNode(head);
            head = newNode;
            this.size++;
            return;
        }
        Node current;
        Node prev = head;
        index--;
        for(int i = 0; i < index; i++) {
            prev = prev.getNextNode();
        }
        current = prev.getNextNode();
        prev.setNextNode(newNode);
        newNode.setNextNode(current);
        this.size++;
    }

    public boolean contains(String data) {
        if(head == null) return false;
        Node current = this.head;
        while (current != null) {
            if (current.getData().equals(data)) {
                return true;
            }
            current = current.getNextNode();
        }
        return false;
    }

    public void delete(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            clear();
            return;
        }
        index--;
        Node current = head;
        Node prev = head;
        for(int i = 0; i < index; i++) {
            prev = prev.getNextNode();
        }
        current = prev.getNextNode();
        prev.setNextNode(current.getNextNode());
        this.size--;
    }

    public void delete(String data) {
        if(head == null) return;
        if (head.getData().equals(data)) {
            head = head.getNextNode();
            this.size--;
            return;
        }

        Node current = head;
        while (current != null) {
            if (current.getNextNode().getData().equals(data)) {
                current.setNextNode(current.getNextNode().getNextNode());
                this.size--;
                return;
            }
            current = current.getNextNode();
        }
    }

    public int size() {
        return size + 1;
    }

    public E get(int index) {
        return (E)getNode(index).getData();
    }

    public void clear() {
        this.head = null;
        this.size = -1;
    }

    public Node getNode(int index) {
//        if (index > size || index < 0) {
//            throw new IndexOutOfBoundsException();
//        }
        Node current = head;
        for(int i = 0; i < index; i++) {
            current = current.getNextNode();
        }
        return current;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && getNode(currentIndex) != null;
            }

            @Override
            public E next() {
                return (E)getNode(currentIndex++).getData();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
