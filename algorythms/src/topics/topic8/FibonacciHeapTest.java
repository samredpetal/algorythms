package topics.topic8;

import java.util.Stack;

//TODO parent child print on paper
/**
 * Реализация кучи Фибоначчи
 */
class FibonacciHeap {
    /**
     * Самая минимальный узел в куче.
     */
    private Node minNode;

    /**
     * Кол-во узлов в куче.
     */
    private int nodesCount;

    /**
     * Новая куча без никаких элементов
     */
    public FibonacciHeap() {

    }

    /**
     * Проверка кучи на пустоту
     */
    public boolean isEmpty() {
        return minNode == null;
    }

    /**
     * Удаляет все элементы с кучи
     */
    public void clear() {
        minNode = null;
        nodesCount = 0;
    }

    /**
     * Уменьшает значений ключа узла, переданное значение ключа будет применено
     * Структра кучи может быть изменено
     */
    public void decreaseKey(Node x, double k) {
        if (k > x.mKey) {
            System.out.println("Значение переданного ключа превышает");
        }

        x.mKey = k;

        Node y = x.mParent;

        if ((y != null) && (x.mKey < y.mKey)) {
            cut(x, y);
            cascadingCut(y);
        }

        if (x.mKey < minNode.mKey) {
            minNode = x;
        }
    }

    /**
     * Удаляет заданный узел с кучи. Ветки будут уплотнены при необходимости.
     */
    public void delete(Node x) {
        // уменьшение узла настолько насколько это возможно
        decreaseKey(x, Double.NEGATIVE_INFINITY);

        // удаление минимального элемента
        removeMin();
    }

    /**
     * Вставляет новый элемент в кучу.
     * Уплотнение кучи не происходит, узел просто вставляется в корневой лист кучи
     */
    public void insert(Node node, double key) {
        node.mKey = key;

        if (minNode != null) {
            node.mLeft = minNode;
            node.mRight = minNode.mRight;
            minNode.mRight = node;
            node.mRight.mLeft = node;

            if (key < minNode.mKey) {
                minNode = node;
            }
        } else {
            minNode = node;
        }

        nodesCount++;
    }

    /**
     * Возвращает самый минимальный элемент в куче.
     * Под самым минимальным элементом предполагается
     * элемент с минимальным ключом
     */
    public Node min() {
        return minNode;
    }

    /**
     * Возвращает элемент с самым минимальный ключом,
     * при необходимости уплотняет кучу
     */
    public Node removeMin() {
        Node z = minNode;

        if (z != null) {
            int numKids = z.mDegree;
            Node x = z.mChild;
            Node tempRight;

            while (numKids > 0) {
                tempRight = x.mRight;

                x.mLeft.mRight = x.mRight;
                x.mRight.mLeft = x.mLeft;

                x.mLeft = minNode;
                x.mRight = minNode.mRight;
                minNode.mRight = x;
                x.mRight.mLeft = x;

                x.mParent = null;
                x = tempRight;
                numKids--;
            }

            z.mLeft.mRight = z.mRight;
            z.mRight.mLeft = z.mLeft;

            if (z == z.mRight) {
                minNode = null;
            } else {
                minNode = z.mRight;
                consolidate();
            }

            nodesCount--;
        }
        return z;
    }

    /**
     * Возвращает размер кучи
     */
    public int size() {
        return nodesCount;
    }

    /**
     * Объединяет две кучи Фибоначчи в одну.
     * Уплотнения кучи не происходит
     */
    public static FibonacciHeap union(FibonacciHeap h1, FibonacciHeap h2) {
        FibonacciHeap h = new FibonacciHeap();

        if ((h1 != null) && (h2 != null)) {
            h.minNode = h1.minNode;

            if (h.minNode != null) {
                if (h2.minNode != null) {
                    h.minNode.mRight.mLeft = h2.minNode.mLeft;
                    h2.minNode.mLeft.mRight = h.minNode.mRight;
                    h.minNode.mRight = h2.minNode;
                    h2.minNode.mLeft = h.minNode;

                    if (h2.minNode.mKey < h1.minNode.mKey) {
                        h.minNode = h2.minNode;
                    }
                }
            } else {
                h.minNode = h2.minNode;
            }

            h.nodesCount = h1.nodesCount + h2.nodesCount;
        }

        return h;
    }

    /**
     * Вывод кучи в текстовом формате
     */
    public String toString() {
        if (minNode == null) {
            return "FibonacciHeap is empty";
        }

        Stack stack = new Stack();
        stack.push(minNode);

        StringBuffer buf = new StringBuffer(512);
        buf.append("FibonacciHeap:\n");

        Stack<String> stringStack = new Stack<>();
        stringStack.push("Parent node: ");
        while (!stack.empty()) {
            Node curr = (Node) stack.pop();
            String string = stringStack.pop();
            buf.append(string);
            buf.append(curr);
            buf.append(" ");

            if (curr.mChild != null) {
                stringStack.push(" Child node: ");
                stack.push(curr.mChild);
            }

            Node start = curr;
            curr = curr.mRight;
            string = " Sibling node: ";

            while (curr != start) {
                buf.append(string);
                buf.append(curr);

                if (curr.mChild != null) {
                    stringStack.push(" Child of sibling: ");
                    stack.push(curr.mChild);
                }

                curr = curr.mRight;
                string = " Sibling node: ";
            }
        }

        buf.append('\n');

        return buf.toString();
    }

    /**
     * Вырезает переданный узел, также делает то же самое с его родителем
     */
    protected void cascadingCut(Node y) {
        Node z = y.mParent;

        // если имеется родитель
        if (z != null) {
            if (!y.mMark) {
                y.mMark = true;
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    /**
     * Уплотняет дерево, объединяя ветки с одинаковыми степенями в одно
     */
    protected void consolidate() {
        int arraySize = nodesCount + 1;
        Node[] array = new Node[arraySize];

        for (int i = 0; i < arraySize; i++) {
            array[i] = null;
        }

        int numRoots = 0;
        Node x = minNode;

        if (x != null) {
            numRoots++;
            x = x.mRight;

            while (x != minNode) {
                numRoots++;
                x = x.mRight;
            }
        }

        while (numRoots > 0) {
            int d = x.mDegree;
            Node next = x.mRight;

            while (array[d] != null) {
                Node y = array[d];

                if (x.mKey > y.mKey) {
                    Node temp = y;
                    y = x;
                    x = temp;
                }

                link(y, x);

                array[d] = null;
                d++;
            }

            array[d] = x;

            x = next;
            numRoots--;
        }

        minNode = null;

        for (int i = 0; i < arraySize; i++) {
            if (array[i] != null) {
                if (minNode != null) {
                    array[i].mLeft.mRight = array[i].mRight;
                    array[i].mRight.mLeft = array[i].mLeft;

                    array[i].mLeft = minNode;
                    array[i].mRight = minNode.mRight;
                    minNode.mRight = array[i];
                    array[i].mRight.mLeft = array[i];

                    if (array[i].mKey < minNode.mKey) {
                        minNode = array[i];
                    }
                } else {
                    minNode = array[i];
                }
            }
        }
    }

    /**
     * Вырезает x узел с потомков узла y
     */
    protected void cut(Node x, Node y) {
        x.mLeft.mRight = x.mRight;
        x.mRight.mLeft = x.mLeft;
        y.mDegree--;

        if (y.mChild == x) {
            y.mChild = x.mRight;
        }

        if (y.mDegree == 0) {
            y.mChild = null;
        }

        x.mLeft = minNode;
        x.mRight = minNode.mRight;
        minNode.mRight = x;
        x.mRight.mLeft = x;

        x.mParent = null;

        x.mMark = false;
    }

    /**
     * Делает переданный узел y дочерним узлом x
     */
    protected void link(Node y, Node x) {
        y.mLeft.mRight = y.mRight;
        y.mRight.mLeft = y.mLeft;

        y.mParent = x;

        if (x.mChild == null) {
            x.mChild = y;
            y.mRight = y;
            y.mLeft = y;
        } else {
            y.mLeft = x.mChild;
            y.mRight = x.mChild.mRight;
            x.mChild.mRight = y;
            y.mRight.mLeft = y;
        }

        x.mDegree++;

        y.mMark = false;
    }

    /**
     * Узел кучи Фибоначчи
     */
    public static class Node {
        /**
         * Первый дочерний узел
         */
        Node mChild;

        /**
         * Левый ближний узел
         */
        Node mLeft;

        /**
         * Родительский узел
         */
        Node mParent;

        /**
         * Правый ближинй узел
         */
        Node mRight;

        /**
         * Истина если текущий узел имеет удаленных дочерних узлов с тех пор как был создан
         */
        boolean mMark;

        /**
         * Ключ узла
         */
        double mKey;

        /**
         * Кол-во дочерних узлов узла (степень узла)
         */
        int mDegree;

        public Node(double key) {
            mRight = this;
            mLeft = this;
            mKey = key;
        }

        public final double getKey() {
            return mKey;
        }


        /**
         * Текстовое представление узла
         */
        public String toString() {
            if (true) {
                return Double.toString(mKey);
            } else {
                StringBuffer buf = new StringBuffer();
                buf.append("Node=[parent = ");

                if (mParent != null) {
                    buf.append(Double.toString(mParent.mKey));
                } else {
                    buf.append("---");
                }

                buf.append(", key = ");
                buf.append(Double.toString(mKey));
                buf.append(", degree = ");
                buf.append(Integer.toString(mDegree));
                buf.append(", right = ");

                if (mRight != null) {
                    buf.append(Double.toString(mRight.mKey));
                } else {
                    buf.append("---");
                }

                buf.append(", left = ");

                if (mLeft != null) {
                    buf.append(Double.toString(mLeft.mKey));
                } else {
                    buf.append("---");
                }

                buf.append(", child = ");

                if (mChild != null) {
                    buf.append(Double.toString(mChild.mKey));
                } else {
                    buf.append("---");
                }

                buf.append(']');

                return buf.toString();
            }
        }
    }
}

/**
 * Тестирование кучи
 */
public class FibonacciHeapTest {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(new FibonacciHeap.Node(233), 213.0d);
        heap.insert(new FibonacciHeap.Node(23), 23.0d);
        System.out.println(heap);
        heap.insert(new FibonacciHeap.Node(13), 13.0d);
        System.out.println(heap);
        heap.removeMin();
        heap.insert(new FibonacciHeap.Node(33), 33.0d);
        System.out.println(heap);
        heap.removeMin();
        System.out.println(heap);
        System.out.println("heap2");
        FibonacciHeap heap2 = new FibonacciHeap();
        heap2.insert(new FibonacciHeap.Node(12), 12.0d);
        heap2.insert(new FibonacciHeap.Node(22), 22.0d);
        heap2.insert(new FibonacciHeap.Node(32), 32.0d);
        heap2.insert(new FibonacciHeap.Node(42), 42.0d);
        System.out.println(heap2);

        FibonacciHeap unionHeap = FibonacciHeap.union(heap, heap2);

        System.out.println("heapUnion");
        System.out.println(unionHeap);

    }
}