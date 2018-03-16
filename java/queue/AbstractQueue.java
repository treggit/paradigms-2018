package queue;

public abstract class AbstractQueue implements Queue {
    protected abstract void add(Object element);

    public void enqueue(Object element) {
        assert element != null;

        add(element);
    }

    protected abstract Object getHead();

    public Object element() {
        assert size() > 0;

        return getHead();
    }

    protected abstract void removeHead();

    public Object dequeue() {
        Object element = element();
        removeHead();
        return element;
    }

    public abstract int size();

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract void clear();

    private boolean equal(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        return a != null && b != null && a.equals(b);
    }

    protected Object[] toArray(int capacity) {
        Object[] array = new Object[capacity];
        for (int i = 0; i < size(); i++) {
            array[i] = dequeue();
            enqueue(array[i]);
        }
        return array;
    }

    public Object[] toArray() {
        return toArray(size());
    }
}
