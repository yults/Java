package queue;

public class ArrayQueueADT {
    // Inv: (n >= 0) && (0 <= i < n: a[i] != null)
    private int size = 0;
    private Object[] elements = new Object[5];
    private int head = 0;
    private int tail = 0;

    // Pre: (el != null) && (que != null)
    public static void enqueue(ArrayQueueADT que, Object el) {
        assert el != null;
        ensureCapacity(que);
        que.elements[que.tail] = el;
        ++que.size;
        que.tail = nextIndex(que, que.tail);
    }
    //Post: (n' == n + 1) && (0 <= i < n: a'[i] == a[i]) && (a'[n] == el)

    // Pre: (n > 0) && (que != null)
    public static Object element(ArrayQueueADT que) {
        assert que.size > 0;
        return que.elements[que.head];
    }
    // Post: queue unchanged && (Result == a[0])

    // Pre: (n > 0) && (que != null)
    public static Object dequeue(ArrayQueueADT que) {
        assert que.size != 0;
        Object first = que.elements[que.head];
        que.head = nextIndex(que, que.head);
        que.size--;
        return first;
    }
    // Post: (n' == n - 1) && (0 <= i < n: a'[i - 1] == a[i]) && (Result == a[0])

    // Pre: que != null
    public static int size(ArrayQueueADT que) {
        return que.size;
    }
    // Post: queue unchanged && (Result == n)

    // Pre: que != null
    public static boolean isEmpty(ArrayQueueADT que) {
        return que.size == 0;
    }
    // Post: queue unchanged && (Result == (n == 0))

    // Pre: que != null
    public static void clear(ArrayQueueADT que) {
        que.size = 0;
        que.head = 0;
        que.tail = 0;
    }
    // Post: n == 0

    private static void ensureCapacity(ArrayQueueADT que) {
        if (size(que) < que.elements.length) {
            return;
        }
        Object[] newObj = new Object[que.elements.length * 2 + 1];
        if (que.tail >= que.head) {
            System.arraycopy(que.elements, que.head, newObj, 0, size(que) - que.head);
            System.arraycopy(que.elements, 0, newObj, size(que) - que.head, que.tail);
        } else {
            System.arraycopy(que.elements, que.head, newObj, 0, size(que));
        }
        que.elements = newObj;
        que.head = 0;
        que.tail = size(que);
    }
    // Post: queue unchanged && (size() < size'() <= size() * 2 + 1)

    // Pre: (size() != 0) && (0 <= x < size()) && (que != null)
    private static int nextIndex(ArrayQueueADT que, int index) {
        return (index + 1) % que.elements.length;
    }
    // Post: Result == (index + 1) % que.size

    public static String toStr(ArrayQueueADT que) {
        StringBuilder ans = new StringBuilder();
        ans.append("[");
        int iterator = que.head;
        for (int itemp = 0; itemp < size(que); itemp++) {
            ans.append(que.elements[iterator]);
            if (itemp != size(que) - 1) {
                ans.append(", ");
            }
            iterator = nextIndex(que, iterator);
        }
        ans.append("]");
        return ans.toString();
    }
    //Post: queue unchanged && (ans == "[elements[head], elements[head + 1], ..., elements[tail]]")
}
