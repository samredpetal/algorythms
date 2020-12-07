package topics.topic8.binomialHeap;

//TODO parent child print
public class BinomialHeap {
    private BinomialHeapNode node;
    private int size;

    /* Constructor */
    public BinomialHeap()
    {
        node = null;
        size = 0;
    }
    /* Check if heap is empty */
    public boolean isEmpty()
    {
        return node == null;
    }
    /* Function to get size */
    public int getSize()
    {
        return size;
    }
    /* clear heap */
    public void makeEmpty()
    {
        node = null;
        size = 0;
    }
    /* Function to insert */
    public void insert(int value)
    {
        if (value > 0)
        {
            BinomialHeapNode temp = new BinomialHeapNode(value);
            if (node == null)
            {
                node = temp;
                size = 1;
            }
            else
            {
                unionNodes(temp);
                size++;
            }
        }
    }

    /* Function to unite two binomial heaps */
    public void merge(BinomialHeapNode binHeap)
    {
        BinomialHeapNode temp1 = node, temp2 = binHeap;

        while ((temp1 != null) && (temp2 != null))
        {
            if (temp1.degree == temp2.degree)
            {
                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            }
            else
            {
                if (temp1.degree < temp2.degree)
                {
                    if ((temp1.sibling == null) || (temp1.sibling.degree > temp2.degree))
                    {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    }
                    else
                    {
                        temp1 = temp1.sibling;
                    }
                }
                else
                {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;
                    if (tmp == node)
                    {
                        node = temp1;
                    }
                    else
                    {

                    }
                }
            }
        }
        if (temp1 == null)
        {
            temp1 = node;
            while (temp1.sibling != null)
            {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        }
        else
        {

        }
    }
    /* Function for union of nodes */
    public void unionNodes(BinomialHeapNode binHeap)
    {
        merge(binHeap);

        BinomialHeapNode prevTemp = null, temp = node, nextTemp = node.sibling;

        while (nextTemp != null)
        {
            if ((temp.degree != nextTemp.degree) || ((nextTemp.sibling != null) && (nextTemp.sibling.degree == temp.degree)))
            {
                prevTemp = temp;
                temp = nextTemp;
            }
            else
            {
                if (temp.key <= nextTemp.key)
                {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                }
                else
                {
                    if (prevTemp == null)
                    {
                        node = nextTemp;
                    }
                    else
                    {
                        prevTemp.sibling = nextTemp;
                    }
                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }
    /* Function to return minimum key */
    public int findMinimum()
    {
        return node.findMinNode().key;
    }

    /* Function to return minimum key */
    public BinomialHeapNode findMinimumNode()
    {
        return node.findMinNode();
    }
    /* Function to delete a particular element */
    public void delete(int value)
    {
        if ((node != null) && (node.findANodeWithKey(value) != null))
        {
            decreaseKeyValue(value, findMinimum() - 1);
            extractMin();
        }
    }
    /* Function to decrease key with a given value */
    public void decreaseKeyValue(int old_value, int new_value)
    {
        BinomialHeapNode temp = node.findANodeWithKey(old_value);
        if (temp == null)
            return;
        temp.key = new_value;
        BinomialHeapNode tempParent = temp.parent;

        while ((tempParent != null) && (temp.key < tempParent.key))
        {
            int z = temp.key;
            temp.key = tempParent.key;
            tempParent.key = z;

            temp = tempParent;
            tempParent = tempParent.parent;
        }
    }
    /* Function to extract the node with the minimum key */
    public int extractMin()
    {
        if (node == null)
            return -1;

        BinomialHeapNode temp = node, prevTemp = null;
        BinomialHeapNode minNode = node.findMinNode();

        while (temp.key != minNode.key)
        {
            prevTemp = temp;
            temp = temp.sibling;
        }

        if (prevTemp == null)
        {
            node = temp.sibling;
        }
        else
        {
            prevTemp.sibling = temp.sibling;
        }

        temp = temp.child;
        BinomialHeapNode fakeNode = temp;

        while (temp != null)
        {
            temp.parent = null;
            temp = temp.sibling;
        }

        if ((node == null) && (fakeNode == null))
        {
            size = 0;
        }
        else
        {
            if ((node == null) && (fakeNode != null))
            {
                node = fakeNode.reverse(null);
                size = node.getSize();
            }
            else
            {
                if ((node != null) && (fakeNode == null))
                {
                    size = node.getSize();
                }
                else
                {
                    unionNodes(fakeNode.reverse(null));
                    size = node.getSize();
                }
            }
        }

        return minNode.key;
    }


    /* Function to display heap */
    public void displayHeap()
    {
        BinomialHeapNode currentNode = this.node;
        while (currentNode != null){
            System.out.print("\nParent node: ");
            System.out.print(currentNode.key);
            System.out.print(" Child nodes: ");
            displayHeap(currentNode.child);
            currentNode = currentNode.sibling;
        }
    }
    public void displayHeap(BinomialHeapNode r)
    {
        if (r != null)
        {
            displayHeap(r.child);
            System.out.print(r.key +", ");
            displayHeap(r.sibling);
        }else{
        }
    }

    public BinomialHeapNode getNode() {
        return node;
    }

    public void setNode(BinomialHeapNode node) {
        this.node = node;
    }
}