package ADT;

import java.util.Map.Entry;
import java.util.function.BiConsumer;

public interface TreeMapInterface<K, V> {
    
    // Add a key-value pair to the TreeMap
    void put(K key, V value);
    
    // Remove a key-value pair from the TreeMap by key
    V remove(K key);
    
    // Get the value associated with the given key
    V get(K key);
    
    // Check if the TreeMap contains the given key
    boolean containsKey(K key);
    
    // Get the size of the TreeMap
    int size();
    
    // Check if the TreeMap is empty
    boolean isEmpty();
    
    // Get the first key in the TreeMap
    K firstKey();
    
    // Get the last key in the TreeMap
    K lastKey();
    
    // Get a subset view of the TreeMap from the start key to the end key
    TreeMapInterface<K, V> subMap(K fromKey, K toKey);
    
    // Get all entries as an iterable
    Iterable<Entry<K, V>> entries();
    
    // Optional: Clear all entries
    void clear();
    
    // Apply a function to each entry
    void forEach(BiConsumer<? super K, ? super V> action);
}
