package topics.topic6;


import java.util.*;

//TODO frequency automate
public class HuffmansCode {


    public static void main(String args[]) {
        List<Node> li = new LinkedList<>();
        // Кол-во букв

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter string:");
        String text = scanner.next();

        Map<Character, Integer> chars = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (chars.containsKey(c)) {
                chars.put(c, chars.get(c) + 1);
            }else{
                chars.put(c, 0);
            }
        }
        String s[] = new String[chars.size()];

        int counter = 0;
        for (Map.Entry<Character, Integer> c : chars.entrySet()) {
            Node node = new Node();
            node.letr = String.valueOf(c.getKey());
            s[counter] = node.letr;
            node.freq = c.getValue();
            li.add(node);
            counter++;
        }

        Node root;
        root = makeHuffmannTree(li);
        System.out.println("Letter\t\tEncoded Form");
        for (int i = 0; i < chars.size(); i++) {
            System.out.print(s[i] + "\t\t\t");
            dfs(root, s[i]);
            System.out.println();
        }
    }


    /**
     * Метод для вывода листа
     * @param li
     */
    public static void printList(List<Node> li) {
        Iterator<Node> it = li.iterator();
        while (it.hasNext()) {
            Node n = it.next();
            System.out.print(n.freq + " ");
        }
        System.out.println();
    }

    /**
     * Метод для образования дерева хаффмана
     * @param li
     * @return
     */
    public static Node makeHuffmannTree(List<Node> li) {
        //Sorting list in increasing order of its letter frequency
        li.sort(new NodeComparator());
        Node temp = null;
        Iterator<Node> it = li.iterator();
        //Loop for making huffman tree till only single node remains in list
        while (true) {
            temp = new Node();
            //a and b are  Node which are to be combine to make its parent
            Node a = new Node(), b = new Node();
            a = null;
            b = null;
            //checking if list is eligible for combining or not
            //here first assignment of it.next in a will always be true as list till end will
            //must have atleast one node
            a = (Node) it.next();
            //Below condition is to check either list has 2nd node or not to combine
            //If this condition will be false, then it means construction of huffman tree is completed
            if (it.hasNext()) {
                b = (Node) it.next();
            }
            //Combining first two smallest nodes in list to make its parent whose frequency
            //will be equals to sum of frequency of these two nodes
            if (b != null) {
                temp.freq = a.freq + b.freq;
                a.data = 0;
                b.data = 1;//assigining 0 and 1 to left and right nodes
                temp.left = a;
                temp.right = b;
                //after combing, removing first two nodes in list which are already combined
                li.remove(0);//removes first element which is now combined  -step1
                li.remove(0);//removes 2nd element which comes on 1st position after deleting first in step1
                li.add(temp);//adding new combined node to list
                //print_list(li);  //For visualizing each combination step
            }
            //Sorting after combining to again repeat above on sorted frequency list
            li.sort(new NodeComparator());
            it = li.iterator();//resetting list pointer to first node (head/root of tree)
            if (li.size() == 1) {
                return (Node) it.next();
            } //base condition ,returning root of huffman tree
        }
    }

    //Function for finding path between root and given letter ch
    public static void dfs(Node n, String ch) {
        Stack<Node> st = new Stack<Node>(); // stack for storing path
        int freq = n.freq; // recording root freq to avoid it adding in path encoding
        findPathAndEncode(st, n, ch, freq);
    }

    //A simple utility function to print stack (Used for printing path)
    public static void printPath(Stack<Node> st) {
        for (int i = 0; i < st.size(); i++) {
            System.out.print(st.elementAt(i).data + " ");
        }
    }

    public static void findPathAndEncode(Stack<Node> st, Node root, String s, int f) {
        //Base condition
        if (root != null) {
            if (root.freq != f) {
                st.push(root);
            } // avoiding root to add in path/encoding bits
            if (root.letr.equals(s)) {
                printPath(st);
                return;
            } // Recursion stopping condition when path gets founded
            findPathAndEncode(st, root.left, s, f);
            findPathAndEncode(st, root.right, s, f);
            //Popping if path not found in right or left of this node,because we previously
            //pushed this node in taking a mindset that it might be in path
            st.pop();
        }
    }
}

/**
 * Узел
 */
class Node {
    String letr = "";
    int freq = 0, data = 0;
    Node left = null, right = null;
}


/**
 * Класс для сравнения и отсортировки списко по их частоте
 */
class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        if (o1.freq > o2.freq) {
            return 1;
        } else if (o1.freq < o2.freq) {
            return -1;
        } else {
            return 0;
        }
    }
}