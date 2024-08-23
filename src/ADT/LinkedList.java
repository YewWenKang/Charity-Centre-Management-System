package ADT;

public class LinkedList<T> implements ListInterface<T> {
    private Node<T> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    private class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    @Override
    public boolean add(T newEntry) {
        Node<T> newNode = new Node<>(newEntry);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        if (newPosition < 1 || newPosition > size + 1) {
            return false;
        }
        Node<T> newNode = new Node<>(newEntry);
        if (newPosition == 1) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = head;
            for (int i = 1; i < newPosition - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(T element) {
        if (head == null) return false;

        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null && !current.next.data.equals(element)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if (givenPosition < 1 || givenPosition > size) {
            return false;
        }
        Node<T> current = head;
        for (int i = 1; i < givenPosition; i++) {
            current = current.next;
        }
        current.data = newEntry;
        return true;
    }

    @Override
    public T getEntry(int givenPosition) {
        if (givenPosition < 1 || givenPosition > size) {
            return null;
        }
        Node<T> current = head;
        for (int i = 1; i < givenPosition; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public boolean contains(T anEntry) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(anEntry)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int getNumberOfEntries() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public T get(int index) {
        return getEntry(index + 1); // Adjust for zero-based indexing
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void remove(int i) {
        if (i < 1 || i > size) {
            return;
        }
        if (i == 1) {
            head = head.next;
        } else {
            Node<T> current = head;
            for (int j = 1; j < i - 1; j++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }
}
