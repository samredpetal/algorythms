package collections.queue;

import java.util.Iterator;

public class RoundQueue<E> implements Iterable<E> {
    private E array[];
    private int f = 0;
    private int r = 0;
    private int currentSize = 0;


    public RoundQueue(int size) {
        this.array = (E[])new Object[size];
    }

    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public E front() {
        if(isEmpty()) throw new IndexOutOfBoundsException();
        return array[f];
    }

    public E dequeue() {
        if(isEmpty()) throw new IndexOutOfBoundsException();
        E temp = array[f];
        array[f] = null;
        f = (f + 1) % array.length;
        this.currentSize--;
        return temp;
    }

    public void enqueue(E e) {
        if(size() == array.length) throw new IndexOutOfBoundsException("Размер полон");
        array[r] = e;
        r = (r + 1) % array.length;
        this.currentSize++;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && array[currentIndex] != null;
            }

            @Override
            public E next() {
                return array[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
