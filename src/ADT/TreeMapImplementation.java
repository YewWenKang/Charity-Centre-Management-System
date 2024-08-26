package ADT;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class TreeMapImplementation<K extends Comparable<K>, V> implements TreeMapInterface<K, V> {

    private final TreeMap<K, V> treeMap;

    // Default constructor
    public TreeMapImplementation() {
        this.treeMap = new TreeMap<>();
    }

    // Constructor with a comparator
    public TreeMapImplementation(Comparator<? super K> comparator) {
        this.treeMap = new TreeMap<>(comparator);
    }

    @Override
    public void put(K key, V value) {
        treeMap.put(key, value);
    }

    @Override
    public V remove(K key) {
        return treeMap.remove(key);
    }

    @Override
    public V get(K key) {
        return treeMap.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return treeMap.containsKey(key);
    }

    @Override
    public int size() {
        return treeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return treeMap.isEmpty();
    }

    @Override
    public K firstKey() {
        return treeMap.firstKey();
    }

    @Override
    public K lastKey() {
        return treeMap.lastKey();
    }

    @Override
    public TreeMapInterface<K, V> subMap(K fromKey, K toKey) {
        TreeMapImplementation<K, V> subMap = new TreeMapImplementation<>();
        treeMap.subMap(fromKey, toKey).forEach(subMap::put);
        return subMap;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return treeMap.entrySet();
    }

    @Override
    public void clear() {
        treeMap.clear();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        treeMap.forEach(action);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Comparator<K> comparator() {
        Comparator<? super K> cmp = treeMap.comparator();
        return (Comparator<K>) cmp;
    }
}
