package collections.linkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add("erke");
        linkedList.add("syima");
        linkedList.addToStart("asylzat");
        linkedList.addToStart("first");
        linkedList.add("last");

        linkedList.addTo("on second", 4);
        linkedList.delete(1);

        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }


    }
}
