package ADT;

import java.util.Set;

public interface DictionaryInterface<K, V> {

    V add(K key, V value);

    V remove(K key);

    V getValue(K key);

    boolean contains(K key);

    boolean isEmpty();

    boolean isFull();

    int getSize();

    Set<K> getKeys();

    void clear();
} 
