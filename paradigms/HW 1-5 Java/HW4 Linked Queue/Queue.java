package queue;

public interface Queue {
    // Inv: (n >= 0) && (0 <= i < n: a[i] != null)

    //Pre: el != null
    void enqueue(Object el);
    //Post: (n' == n + 1) && (0 <= i < n: a'[i] == a[i]) && (a'[n] == el)

    //Pre: n > 0;
    Object element();
    // Post: queue unchanged && (Result == a[0])

    //Pre: n > 0;
    Object dequeue();
    //Post: (n' == n - 1) && (0 <= i < n: a'[i - 1] == a[i]) && (Result == a[0])

    int size();
    // Post: queue unchanged && (Result == n)

    boolean isEmpty();
    // Post: queue unchanged && (Result == (n == 0))

    void clear();
    //Post: n = 0;

    Object[] toArray();
    //Post: queue unchanged

}
