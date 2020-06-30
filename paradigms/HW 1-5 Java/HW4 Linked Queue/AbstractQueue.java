package queue;

import java.util.Arrays;

public abstract class AbstractQueue  implements Queue {

    protected int size = 0;

    protected abstract void enqueueAbst(Object el);

    public void enqueue(Object el) {
        assert el != null;
        enqueueAbst(el);
        size++;
    }

    protected abstract Object elementAbst();

    public Object element() {
        assert size > 0;
        return elementAbst();
    }

    protected abstract void dequeueAbst();

    public Object dequeue() {
        assert size != 0;
        Object first = element();
        dequeueAbst();
        size--;
        return first;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract void clearAbst();

    public void clear() {
        size = 0;
        clearAbst();
    }

    protected abstract Object[] toArrayAbst(Object[] Array);

    public Object[] toArray() {
        Object[] Array = new Object[size];
        return toArrayAbst(Array);
    }

    public String toStr() {
        return Arrays.toString(toArray());
    }

}