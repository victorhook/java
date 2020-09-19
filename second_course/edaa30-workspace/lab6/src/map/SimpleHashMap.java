package map;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleHashMap<K, V> implements Map<K, V> {

    private int size, capacity;
    private double loadFactor;

    private static final int DEFAULT_CAPACITY = 16, GROW_FACTOR = 2;
    private static final double DEFAULT_LOAD_FACTOR = .75;

    private Entry<K, V>[] table;

    public SimpleHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleHashMap(int capacity) {
        this.capacity = capacity;
        size = 0;
        loadFactor = DEFAULT_LOAD_FACTOR;
        table = (Entry<K, V>[]) new Entry[capacity];
    }

    private int index(K key) {
        return key.hashCode() & (capacity - 1);
    }

    /**
     * Helper-method to find an entry in the table with a key.
     * @param index
     * @param key
     * @return entry
     */
    private Entry<K,V> find(int index, K key) {
        Entry<K, V> node = table[index];
        int hash = key.hashCode();
        while (node != null) {
            if (node.hash == hash && node.key.equals(key)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * Returns the value found by the given key (if it exists in the table).
     * If the key doesn't exists, null is returned.
     * @param obj
     * @return
     */
    @Override
    public V get(Object obj) {
        K key = (K) obj;
        System.out.printf("Obj: %s\n", index((K) obj));
        Entry<K, V> node = find(index(key), key);
        return node != null ? node.value : null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Helper method to tell if we need to increase the capacity of the table.
     * @return
     */
    private boolean shouldIncrease() {
        return (size / (double) capacity ) >= loadFactor;
    }

    /**
     * Puts the given key-value pair in the table by getting the key's index
     * (from a hash-function), and if the index is empty, insert it, otherwise
     * build onto a single-linked list from the given index, with the entry's "next" attribute.
     * To compare a given key, first the index is compared, then the hashes and finally the .equals(obj)
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) {
        int index = index(key);
        int hash = key.hashCode();
        Entry<K, V> node = table[index], prev = null;

        if (node == null) {
            table[index] = new Entry<>(key, value);
        }
        else {
            while (node != null) {
                if (node.hash == hash && node.key.equals(key)) {
                    V oldValue = node.value;
                    node.setValue(value);
                    return oldValue;
                }
                prev = node;
                node = node.next;
            }
            prev.next = new Entry<>(key, value);
        }

        size++;
        if (shouldIncrease()) {
            rehash();
        }
        return null;
    }

    /**
     * Increases the size of the hash-table by a factor of "GROW_FACTOR"
     * The capacity attribute is increased and new hashes are computed
     * for every pair in the table.
     */
    private void rehash() {
        Entry<K, V> tmp[] = table;
        table = (Entry<K, V>[]) new Entry[capacity *= GROW_FACTOR];
        Entry<K, V> node;
        size = 0;

        for (int entry = 0; entry < tmp.length; entry++) {
            node = tmp[entry];
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new SimpleHashMap<>();
        for (int i = 1; i <= 100; i++) {
            map.put(i, i);
        }
        System.out.printf("[Size: %s\n", map.size());
    }

    @Override
    public V remove(Object obj) {
        K key = (K) obj;
        int index = index(key);
        int hash = key.hashCode();
        Entry<K, V> node = table[index], prev = null;

        while (node != null) {
            if (node.hash == hash && node.key.equals(key)) {
                V oldValue = node.value;
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return oldValue;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    public String toString() {
        StringJoiner sj = new StringJoiner("\n");
        StringBuilder line = new StringBuilder();

        Entry<K, V> node;
        for (int entry = 0; entry < capacity; entry++) {
            node = table[entry];
            line.append(entry);

            if (node == null) {
                line.append(String.format("\t\t\t%s", "EMPTY"));
            } else {
                while (node != null) {
                    line.append(String.format("\t\t\t%s", node));
                    node = node.next;
                }
            }
            sj.add(line);
            line.setLength(0);

        }
        return sj.toString();
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;
        private int hash;
        private Entry<K, V> next;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
            next = null;
        }

        /*
            Enables boolean check with the entire entry instead of accessing
            the key attribute. Not sure if good/bad?
         */
        public boolean equals(Object obj) {
            return this.key.equals((K) obj);
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
