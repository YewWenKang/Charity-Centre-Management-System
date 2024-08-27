// File: ADT/HashMapImplementation.java
package ADT;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashMapImplementation<K, V> implements HashMapInterface<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private LinkedList<Node<K, V>>[] table;
    private int size;

    public HashMapImplementation() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size = 0;
    }

    private int getIndex(K key) {
        return key.hashCode() % table.length;
    }

    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Node<K, V> node : table[index]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        table[index].add(new Node<>(key, value));
        size++;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        if (table[index] != null) {
            for (Node<K, V> node : table[index]) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return (value != null) ? value : defaultValue;
    }

    @Override
    public void remove(K key) {
        int index = getIndex(key);
        if (table[index] != null) {
            for (Node<K, V> node : table[index]) {
                if (node.key.equals(key)) {
                    table[index].remove(node);
                    size--;
                    return;
                }
            }
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (LinkedList<Node<K, V>> bucket : table) {
            if (bucket != null) {
                for (Node<K, V> node : bucket) {
                    keys.add(node.key);
                }
            }
        }
        return keys;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = new HashSet<>();
        for (LinkedList<Node<K, V>> bucket : table) {
            if (bucket != null) {
                for (Node<K, V> node : bucket) {
                    entrySet.add(new AbstractMap.SimpleEntry<>(node.key, node.value));
                }
            }
        }
        return entrySet;
    }

    private static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
