package info.kgeorgiy.ja.yulcova.arrayset;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements SortedSet<T> {
    private final ArrayList<T> elements;
    private final Comparator<? super T> comparator;

    public ArraySet() {
        this(new ArrayList<>(), null);
    }

    public ArraySet(Collection<? extends T> coll) {
        this(coll, null);
    }

    public ArraySet(Comparator<? super T> cmp) {
        this(new ArrayList<>(), cmp);
    }

    public ArraySet(Collection<? extends T> coll, Comparator<? super T> cmp) {
        comparator = cmp;
        Set<T> treeSet = new TreeSet<>(cmp);
        treeSet.addAll(coll);
        elements = new ArrayList<>(treeSet);
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, toElement, false);
    }
    public SortedSet<T> subSet(T fromElement, T toElement, boolean ch) {
        if (notIllegalArgs(fromElement, toElement)) {
            int from = Collections.binarySearch(elements, fromElement, comparator);
            int to = Collections.binarySearch(elements, toElement, comparator);
            if (ch && to >= 0) {
                to++;
            }
            return new ArraySet<>(elements.subList(newIndex(from), newIndex(to)), comparator);
        }
        else {
            throw new IllegalArgumentException("Wrong bounds");
        }
    }

    @Override
    public T first() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("No first element");
        }
        return elements.get(0);
    }

    @Override
    public T last() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("No last element");
        }
        return elements.get(elements.size() - 1);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }
	@SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return Collections.binarySearch(elements, (T) o, comparator) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(elements).iterator();
    }
	@SuppressWarnings("unchecked")
    private boolean notIllegalArgs(T fromElement, T toElement) {
        int flag = comparator == null ? ((Comparable<T>) fromElement).compareTo(toElement) : comparator.compare(fromElement, toElement);
        return flag <= 0;
    }

    private int newIndex(int inx) {
        return inx < 0 ? -inx - 1 : inx;
    }
    @Override
    public SortedSet<T> headSet(T toElement) {
        if (isEmpty()) {
            return this;
        }
        if (!notIllegalArgs(first(), toElement)) {
            return new ArraySet<>(Collections.emptyList(), comparator);
        }
        return subSet(first(), toElement, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (isEmpty()) {
            return this;
        }
        if (!notIllegalArgs(fromElement, last())) {
            return new ArraySet<>(Collections.emptyList(), comparator);
        }
        return subSet(fromElement, last(), true);
    }
}