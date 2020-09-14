package map;

import java.util.LinkedList;
import java.util.List;

public class SimpleHashMap<K, V> implements Map<K, V> {

    private int size, capacity;
    private double loadFactor;

    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = .75;

    private Entry<K, V>[] entries;

    public SimpleHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleHashMap(int capacity) {
        this.capacity = capacity;
        size = 0;
        loadFactor = DEFAULT_LOAD_FACTOR;
        entries = (Entry<K, V>[]) new Entry[capacity];
    }

    private int index(K key) {
        int i = key.hashCode();
        while (entries[i] != null) {
            i++;
        }
    }

    private Entry<K,V> find(int index, K key) {
        return entries[index(key)]
    }


    @Override
    public V get(Object arg0) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V put(K arg0, V arg1) {
        return null;
    }

    @Override
    public V remove(Object arg0) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    public String toString() {
        for (int entry = 0; entry < size; entry++) {
            System.out.printf("%s\t%s\n", entries[entry]);
        }
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        private <K, V> Entry() {
            key = null;
            value = null;
            next = null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        public String toString() {
            return String.format("%s=%s", key, value);
        }


    }

}
