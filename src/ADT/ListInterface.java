package ADT;

public interface ListInterface<T> {

  boolean add(T newEntry);

  boolean add(int newPosition, T newEntry);

  boolean remove(T element);

  void clear();

  boolean replace(int givenPosition, T newEntry);

  T getEntry(int givenPosition);

  boolean contains(T anEntry);

  int getNumberOfEntries();

  boolean isEmpty();

  boolean isFull();
  
  T get(int index);

  int size();

  void remove(int i);
}

