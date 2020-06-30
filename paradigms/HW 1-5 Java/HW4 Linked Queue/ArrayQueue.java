package queue;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(){
        elements = new Object[5];
    }

    public ArrayQueue(int siz) {
        elements = new Object[siz];
    }

    protected void enqueueAbst (Object el) {
        ensureCapacity();
        elements[tail] = el;
        tail = nextIndex(tail);
    }

    protected Object elementAbst() {
        return elements[head];
    }

    protected void dequeueAbst() {
        head = nextIndex(head);
    }

    protected void clearAbst() {
        head = 0;
        tail = 0;
    }

    private void ensureCapacity() {
        if (size() < elements.length) {
            return;
        }
        Object[] newObj = new Object[elements.length * 2 + 1];
        elements = makeCopy(newObj);
        head = 0;
        tail = size();
    }

    private int nextIndex(int index) {
        return (index + 1) % elements.length;
    }

    protected Object[] toArrayAbst(Object[] Array) {
        if (size == 0) return Array;
        return makeCopy(Array);
    }

    private Object[] makeCopy(Object[] newObj) {
        if (tail <= head) {
            int itemp = elements.length - head;
            System.arraycopy(elements, head, newObj, 0, itemp);
            System.arraycopy(elements, 0, newObj, itemp, tail);
        } else {
            System.arraycopy(elements, head, newObj, 0, size());
        }
        return newObj;
    }

}