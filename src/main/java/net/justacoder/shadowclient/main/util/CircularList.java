package net.justacoder.shadowclient.main.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CircularList<T> implements Iterable<T> {

    private final ArrayList<T> items;
    private final int capacity;

    public CircularList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Cannot create a CircularList with length 0 or below");
        }
        this.items = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    public void add(T object) {
        if (items.size() == capacity) {
            items.removeFirst();
        }
        items.add(object);
    }

    public void remove(T object) {
        items.remove(object);
    }

    public void remove(int index) {
        items.remove(index);
    }

    public T get(int index) {
        return items.get(index % capacity);
    }

    public T getFirst() {
        return items.getFirst();
    }

    public T getLast() {
        return items.getLast();
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public int indexOf(T object) {
        return items.indexOf(object);
    }

    public int size() {
        return items.size();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        items.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return items.spliterator();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
