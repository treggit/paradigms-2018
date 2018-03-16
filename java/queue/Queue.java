package queue;

//queue not changed: size == size' && q[i] == q[i]' forall queue elements

public interface Queue {
    //Pre: element != null
    //Post: queue not changed
    void enqueue(Object element);

    //Pre: size != 0
    //Post: result == q[0] && size' == size - 1 && q[i] == q[i]' forall i = 1..size - 1
    Object dequeue();

    //Pre: size != 0
    //Post: result == q[0] && queue not changed
    Object element();

    //Pre: true
    //Post: result == size && queue not changed
    int size();

    //Pre: true
    //Post: result == (size > 0) && queue not changed
    boolean isEmpty();

    //Pre: true
    //Post: size' == 0
    void clear();

    //Pre: true
    //Post: result == queue as array
    Object[] toArray();
}
