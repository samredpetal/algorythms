package collections.queue;

public class Queue<E> {
    private E array[];
    private int size = 0;
    private int rear = -1;

    public Queue() {
        this.array = (E[])new Object[10];
    }

    public Queue(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    public boolean enqueue(E data) {
        try {
            this.rear++;
            array[rear] = data;
            this.size++;
            return true;
        } catch (Exception e) {
            E[] newArray = (E[])new Object[array.length + 10];
            System.arraycopy(this.array, 0, newArray, 0, this.size());
            this.array = newArray;
            enqueue(data);
        }
        return false;
    }

    public E dequeue() {
        if(size() == 0) return null;
        else{
            E data = this.array[0];
            this.array[0] = null;
            this.rear--;
            this.size--;
            E[] newArray = (E[])new Object[this.array.length];
            System.arraycopy(this.array, 1, newArray, 0, this.size());
            this.array = newArray;
            return data;
        }
    }

    public E search(E data) {
        for (int i = 0; i < size(); i++) {
            if(array[i].equals(data)) return array[i];
        }
        return null;
    }

    public boolean contains(E data) {
        return search(data) != null;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public E front() {
        return array[0];
    }

    public void clear() {
        this.size = 0;
        this.rear = -1;
        this.array = (E[])new Object[10];
    }

    public E peek() {
        return array[rear];
    }


}
