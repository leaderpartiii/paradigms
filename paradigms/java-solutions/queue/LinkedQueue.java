package queue;

public class LinkedQueue extends AbstractQueue implements Queue {
    private Node tail;
    private Node head;

    protected static class Node {
        public Node(Object item, Node next) {
            this.item = item;
            this.next = next;
        }

        private final Object item;
        private Node next;
    }

    //Post: head.item;
    protected Object elementImpl() {
        return head.item;
    }

    //Post: tail = (item, null);
    protected void enqueueImpl(Object item) {
        Node newTail = new Node(item, null);
        if (isEmpty()) {
            head = newTail;
            head.next = tail;
        } else {
            tail.next = newTail;
        }
        tail = newTail;
    }

    protected void dequeueImpl() {
        head = head.next;
    }

    //Post: head = tail = null
    public void clearImpl() {
        head = tail = null;
    }
}
