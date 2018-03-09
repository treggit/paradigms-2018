package queue;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[5];
    private int head = 0, tail = 0;

    private void ensureCapacity(int capacity) {
        if (capacity < elements.length) {
            return;
        }

        int newTail = size();
        elements = toArray(capacity * 2);
        tail = newTail;
        head = 0;
    }

    protected void add(Object element) {
        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = inc(tail);
    }

    protected void removeHead() {
        elements[head] = null;
        head = inc(head);
    }

    protected Integer getHead() {
        return head;
    }

    protected Integer getTail() {
        return tail;
    }

    protected Object get(Object position) {

        return elements[(Integer) position];
    }

    protected Integer inc(Object position) {
        return (((Integer) position) + 1) % elements.length;
    }

    public int size() {
        if (head <= tail) {
            return tail - head;
        } else {
            return tail + elements.length - head;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        head = 0;
        tail = 0;
        elements = new Object[5];
    }
}
