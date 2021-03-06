package queue;

public class LinkedQueue extends AbstractQueue{
    private Node head = null, tail = null;
    private int size = 0;

    private class Node {
        private Object value;
        private Node next;

        private Node(Object value, Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
        }
    }


    protected void add(Object element) {
        Node newNode = new Node(element, null);

        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    protected void removeHead() {
        Node newHead = head.next;
        head = null;
        head = newHead;
        size--;
    }

    protected Object getHead() {
        return head.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }
}
