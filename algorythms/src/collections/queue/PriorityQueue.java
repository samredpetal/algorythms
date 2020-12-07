package collections.queue;

public class PriorityQueue<E> {
    private E array[];
    private int priorities[];
    private int currentSize = 0;

    public PriorityQueue() {
        array = (E[]) new Object[10];
        priorities = new int[10];
    }

    public void insertItem(int priority, E data) {
        try {
            priorities[currentSize] = priority;
            array[currentSize] = data;
            currentSize++;
        } catch (Exception e) {
            E newArray[] = (E[]) new Object[array.length + 10];
            System.arraycopy(array, 0, newArray, array.length, currentSize);
            this.array = newArray;
            insertItem(priority, data);
        }
    }

    public int size() {
        return this.currentSize;
    }

    public int minKey() {
        int minKey = priorities[0];
        for(int i = 0; i < currentSize; i++) {
            if(priorities[i] < minKey) minKey = priorities[i];
        }
        return minKey;
    }

    public E minElement() {
        if(isEmpty()) throw new IndexOutOfBoundsException();
        E minElement = array[0];
        for(int i = 0; i < currentSize; i++) {
            if (minKey() == priorities[i]) {
                minElement = array[i];
                break;
            }
        }
        return minElement;
    }

    public E removeMin() {
        E temp = minElement();
        int minKey = minKey();
        for(int i = 0; i < currentSize; i++) {
            if (minKey == priorities[i]) {
                for(int j = i;  j < currentSize - 1; j++) {
                    array[j] = array[j+1];
                    priorities[j] = priorities[j+1];
                }
                break;
            }
        }
        currentSize--;
        return temp;
    }

    public boolean isEmpty() {
        return size() == 0;
    }



}
