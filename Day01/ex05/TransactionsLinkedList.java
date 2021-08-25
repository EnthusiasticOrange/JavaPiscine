class TransactionNotFoundException extends RuntimeException {
}

class TransactionsLinkedList implements TransactionsList {

    class NodeBase {
        public NodeBase prev;
        public NodeBase next;

        public NodeBase() {
            this.prev = this;
            this.next = this;
        }
    }

    class Node extends NodeBase {
        public Transaction t;

        public Node(Transaction t) {
            this.t = t;
        }
    }

    private int size;
    private NodeBase root;

    public TransactionsLinkedList() {
        this.size = 0;
        this.root = new NodeBase();
    }

    public void add(Transaction t) {
        Node n = new Node(t);

        n.next = this.root;
        n.prev = this.root.prev;
        this.root.prev.next = n;
        this.root.prev = n;
        this.size += 1;
    }

    public Transaction remove(String id) {
        NodeBase tmp = this.root.next;
        while (tmp != this.root) {
            Node node = (Node) tmp;
            if (node.t.getId().equals(id)) {
                tmp.prev.next = tmp.next;
                tmp.next.prev = tmp.prev;
                this.size -= 1;
                return node.t;
            }
            tmp = tmp.next;
        }
        throw new TransactionNotFoundException();
    }

    public Transaction[] toArray() {
        Transaction[] arr = new Transaction[this.size];
        NodeBase node = this.root.next;
        for (int i = 0; i < this.size; ++i) {
            Node tmp = (Node) node;
            arr[i] = tmp.t;
            node = node.next;
        }
        return arr;
    }

    public boolean contains(String id) {
        NodeBase tmp = this.root.next;
        while (tmp != this.root) {
            Node node = (Node) tmp;
            if (node.t.getId().equals(id)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    public int getSize() {
        return this.size;
    }
}