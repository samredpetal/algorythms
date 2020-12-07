package collections.stack;

import java.util.Iterator;

public class Stack<E> implements Iterable<E>{
    private E array[];
    private int size = 0;
    private int top = -1;

    public Stack() {
        this.array = (E[])new Object[1];
    }

    public Stack(int size) {
        this.top++;
        this.size = size;
        this.array = (E[])new Object[size];
    }

    public void push(E data) {
        try {
            this.array[top + 1] = data;
            this.size++;
            this.top++;
        } catch (IndexOutOfBoundsException e) {
            this.size = this.size+10;
            E[] newArray = (E[])new Object[this.size];
            System.arraycopy(this.array, 0, newArray, 0, this.size());
            this.array = newArray;
            push(data);
        }
    }

    public void push(E data, int index) {
        try {
            E indexData = this.array[index];
            this.array[index] = data;
            E currentData = indexData;
            for(int i = index + 1; i <= top + 1; i++) {
                E nextData = this.array[i];
                this.array[i] = currentData;
                currentData = nextData;
            }
            this.size++;
            this.top++;
        } catch (IndexOutOfBoundsException e) {
            E[] newArray = (E[])new Object[this.size+10];
            System.arraycopy(this.array, 0, newArray, 0, this.size());
            this.array = newArray;
            this.size = this.size*2;
            push(data);
        }
    }

    public E get(int i) {
        return this.array[i];
    }

    public E peek() {
        if(isEmpty()) return null;
        return array[top];
    }

    public E pop() {
        if(isEmpty()) return null;
        E top = array[this.top];
        array[this.top] = null;
        this.top--;
        return top;
    }

    public boolean contains(E e) {
        return search(e) > -1;
    }

    public int search(E data) {
        for (int i = 0; i < this.size(); i++) {
            if (array[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public Stack<E> getInversed() {
        Stack<E> currentStack = this;
        Stack<E> inversedStack = new Stack<>();
        for(int i = size() - 1; i >= 0; i--) {
            inversedStack.push(currentStack.pop());
        }
        return inversedStack;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return top + 1;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && array[currentIndex] != null;
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
