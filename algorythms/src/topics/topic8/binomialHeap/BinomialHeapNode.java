package topics.topic8.binomialHeap;

class BinomialHeapNode
{
    int key, degree;
    BinomialHeapNode parent;
    BinomialHeapNode sibling;
    BinomialHeapNode child;

    /* Constructor */
    public BinomialHeapNode(int k)
    {
        key = k;
        degree = 0;
        parent = null;
        sibling = null;
        child = null;
    }
    /* Function reverse */
    public BinomialHeapNode reverse(BinomialHeapNode sibl)
    {
        BinomialHeapNode ret;
        if (sibling != null)
            ret = sibling.reverse(this);
        else
            ret = this;
        sibling = sibl;
        return ret;
    }
    /* Function to find min node */
    public BinomialHeapNode findMinNode()
    {
        BinomialHeapNode x = this, y = this;
        int min = x.key;

        while (x != null) {
            if (x.key < min) {
                y = x;
                min = x.key;
            }
            x = x.sibling;
        }

        return y;
    }
    /* Function to find node with key value */
    public BinomialHeapNode findANodeWithKey(int value)
    {
        BinomialHeapNode temp = this, node = null;

        while (temp != null)
        {
            if (temp.key == value)
            {
                node = temp;
                break;
            }
            if (temp.child == null)
                temp = temp.sibling;
            else
            {
                node = temp.child.findANodeWithKey(value);
                if (node == null)
                    temp = temp.sibling;
                else
                    break;
            }
        }

        return node;
    }
    /* Function to get size */
    public int getSize()
    {
        return (1 + ((child == null) ? 0 : child.getSize()) + ((sibling == null) ? 0 : sibling.getSize()));
    }


}