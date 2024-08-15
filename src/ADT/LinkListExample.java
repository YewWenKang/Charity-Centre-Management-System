package ADT;

/**
 * A demo class to showcase the usage of LinkedList with Integer type.
 */
public class LinkListExample {
    public static void main(String[] args) {
        // Create a new LinkedList of Integer type
        LinkedList<Integer> integerList = new LinkedList<>();
        
        // Add elements to the list
        integerList.add(10); // Adds 10 at the end
        integerList.add(20); // Adds 20 at the end
        
        // Print the list
        System.out.println("List after adding 10 and 20:");
        printList(integerList);
        
        // Add an element at a specific position
        integerList.add(2, 15); // Adds 15 at position 2
        
        // Print the list
        System.out.println("List after adding 15 at position 2:");
        printList(integerList);
        
        // Get an element
        System.out.println("Element at position 2: " + integerList.getEntry(2));
        
        // Remove an element
        integerList.remove(2); // Removes the element at position 2
        
        // Print the list
        System.out.println("List after removing element at position 2:");
        printList(integerList);
        
        // Replace an element
        integerList.replace(1, 100); // Replaces element at position 1 with 100
        
        // Print the list
        System.out.println("List after replacing element at position 1 with 100:");
        printList(integerList);
        
        // Check if the list contains an element
        System.out.println("Does the list contain 100? " + integerList.contains(100));
        
        // Check if the list contains 10
        System.out.println("Does the list contain 10? " + integerList.contains(10));
        
        // Clear the list
        integerList.clear();
        
        // Print the list
        System.out.println("List after clearing:");
        printList(integerList);
    }
    
    // Helper method to print the list
    private static <T> void printList(LinkedList<T> list) {
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            System.out.print(list.getEntry(i) + " ");
        }
        System.out.println();
    }
}
