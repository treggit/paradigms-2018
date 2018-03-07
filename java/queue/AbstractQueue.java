package queue;

public abstract class AbstractQueue implements Queue {
    protected abstract void add(Object element);

    public void enqueue(Object element) {
        assert element != null;

        add(element);
    }

    public Object element() {
        assert size() > 0;

        return get(getHead());
    }

    protected abstract void removeHead();

    public Object dequeue() {
        assert size() > 0;

        Object element = element();
        removeHead();
        return element;
    }

    public abstract int size();

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract void clear();

    protected abstract Object get(Object obj);
    protected abstract Object getHead();
    protected abstract Object getTail();
    protected abstract Object inc(Object obj);

    protected Object[] toArray(int capacity) {
        Object[] array = new Object[capacity];
        int j = 0;
        for (Object i = getHead(); !i.equals(getTail()); i = inc(i), j++) {
            array[j] = get(i);
        }
        return array;
    }

    public Object[] toArray() {
        return toArray(size());
    }
}
