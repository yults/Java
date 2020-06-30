package queue;

public class LinkedQueue extends AbstractQueue {
    private class Block {
        private Block next;
        private Object value;
        private Block(Block newNext, Object newValue) {
            next = newNext;
            value = newValue;
        }
    }

    private Block head, tail;

    protected void enqueueAbst(Object el) {
        if (size == 0) {
            head = new Block(null, el);
            tail = head;
        } else {
            tail.next = new Block(null, el);
            tail = tail.next;
        }
    }

    protected Object elementAbst() {
        return head.value;
    }

    protected void dequeueAbst() {
        head = head.next;
    }

    protected void clearAbst() {
        head = null;
        tail = null;
    }

    protected Object[] toArrayAbst(Object[] Array) {
        Block el = head;
        for (int i = 0; i < size; i++) {
            Array[i] = el.value;
            el = el.next;
        }
        return Array;
    }

}