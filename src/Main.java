
import ADT.LinkedList;


public class Main {
    
    
    
    private static <T> void printList(LinkedList<T> list) {
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            System.out.print(list.getEntry(i) + " ");
        }
        System.out.println();
    }
    
}
