// File: ADT/HashMapInterface.java
package ADT;

import java.util.Map;
import java.util.Set;

public interface HashMapInterface<K, V> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    boolean containsKey(K key);
    boolean isEmpty();
    int size();
    V getOrDefault(K key, V defaultValue);
    Set<K> keySet(); // Add this method
    Set<Map.Entry<K, V>> entrySet(); // Ensure this method is also present
}
