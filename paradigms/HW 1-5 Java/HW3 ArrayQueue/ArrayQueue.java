package queue;

import java.util.Objects;

public class ArrayQueue {
    // Inv: (n >= 0) && (0 <= i < n: a[i] != null)
    private int size = 0;
    private Object[] elements = new Object[5];
    private int head = 0;
    private int tail = 0;

    // Pre: (el != null)
    public void enqueue(Object el) {
        assert el != null;
        ensureCapacity();
        elements[tail] = el;
        ++size;
        tail = nextIndex(tail);
    }
    // Post: (n' == n + 1) && (0 <= i < n: a'[i] == a[i]) && (a'[n] == el)

    // Pre: n > 0
    public Object element() {
        assert size > 0;
        return elements[head];
    }
    // Post: queue unchanged && (Result == a[0])

    // Pre: n > 0
    public Object dequeue() {
        assert size() != 0;
        Object first = elements[head];
        head = nextIndex(head);
        size--;
        return first;
    }
    // Post: (n' == n - 1) && (0 <= i < n: a'[i - 1] == a[i]) && (Result == a[0])

    public int size() {
        return size;
    }
    // Post: queue unchanged && (Result == n)

    public boolean isEmpty() {
        return size == 0;
    }
    // Post: queue unchanged && (Result == (n == 0))

    public void clear() {
        size = 0;
        head = 0;
        tail = 0;
    }
    //Post: n == 0


    private void ensureCapacity() {
        if (size() < elements.length) {
            return;
        }
        Object[] newObj = new Object[elements.length * 2 + 1];
        if (tail >= head) {
            System.arraycopy(elements, head, newObj, 0, size() - head);
            System.arraycopy(elements, 0, newObj, size() - head, tail);
        } else {
            System.arraycopy(elements, head, newObj, 0, size());
        }
        elements = newObj;
        head = 0;
        tail = size();
    }
    // Post: queue unchanged && (size() < size'() <= size() * 2 + 1)

    // Pre: (size() != 0) && (0 <= x < size())
    private int nextIndex(int index) {
        return (index + 1) % elements.length;
    }
    //Post: Result == (index + 1) % size()

    public String toStr() {
        StringBuilder ans = new StringBuilder();
        ans.append("[");
        int iterator = head;
        for (int itemp = 0; itemp < size(); itemp++) {
            ans.append(elements[iterator]);
            if (itemp != size() - 1) {
                ans.append(", ");
            }
            iterator = nextIndex(iterator);
        }
        ans.append("]");
        return ans.toString();
    }
    //Post: queue unchanged && (ans == "[elements[head], elements[head + 1], ..., elements[tail]]")
}
