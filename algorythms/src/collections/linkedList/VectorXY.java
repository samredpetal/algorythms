package collections.linkedList;

public class VectorXY<E> {
    public E x;
    public E y;

    public VectorXY(E x, E y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" +
                "x=" + x +
                ", y=" + y +
                ']';
    }

}
