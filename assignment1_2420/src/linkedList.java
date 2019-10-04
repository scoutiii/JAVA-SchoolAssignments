// The linked list class which is generic, specifically made to work as a queue (first in first out, unsorted)

class linkedList<G> {
    // References the first item in the list, starts as null
    private node<G> head;
    private node<G> tail = null;
    // contains a running total length of list (so its max length)
    private long runningLength;
    private long currentLength;

    // Constructor takes an item and makes it the head
    linkedList(G item) {
        this.head = new node<>(item);
        this.runningLength = 1;
        this.currentLength = 1;
    }

    // Method to insert item to end of list
    void insert(G item) {
        node<G> newItem = new node<>(item);

        // Base case, if list is only one item
        if(this.currentLength <= 1) {
            this.head.setNext(newItem);
            this.tail = newItem;
        }
        // This is what makes inserting fast for a queue, just keeping track of the tail
        else {
            this.tail.setNext(newItem);
            this.tail = newItem;
        }
        this.currentLength += 1;
        this.runningLength += 1;
    }

    // Removes the first item in the list
    void removeFront() {
        // To prevent null pointer exceptions, just in case
        if(this.head != null){
            this.head = this.head.getNext();
            this.currentLength -= 1;
        }
    }

    // Returns the max length of the list
    long getRunningLength() {
        return this.runningLength;
    }

    // Returns the current length of the list
    long getLength() {
        return this.currentLength;
    }

    // Returns the item that is the head
    G getHead() {
        return this.head.getItem();
    }

    /* This is the old main, used to test the linked list, not needed */
//    public static void main(String[] args) {
//        Board a = new Board();
//        int[] nums1 = {1,1,1,1,1,1,1,1,1};
//        a.makeBoard(nums1);
//
//        linkedList<Board> test = new linkedList<>(a);
//        test.printList();
//        System.out.println(test.getRunningLength());
//        System.out.println(test.getLength());
//
//        Board b = new Board();
//        int[] nums2 = {2,2,2,2,2,2,2,2,2};
//        b.makeBoard(nums2);
//        test.insert(b);
//        test.printList();
//        System.out.println(test.getRunningLength());
//        System.out.println(test.getLength());
//
//        Board c = new Board();
//        int[] nums3 = {3,3,3,3,3,3,3,3,3};
//        c.makeBoard(nums3);
//        test.insert(c);
//        test.printList();
//        System.out.println(test.getRunningLength());
//        System.out.println(test.getLength());
//
//        test.removeFront();
//        test.printList();
//        System.out.println(test.getRunningLength());
//        System.out.println(test.getLength());
//
//        test.removeFront();
//        test.printList();
//        System.out.println(test.getRunningLength());
//        System.out.println(test.getLength());
//
//        test.removeFront();
//        test.printList();
//        System.out.println(test.getRunningLength());
//        System.out.println(test.getLength());
//
//    }

    /* Method which prints out the list, also not needed */
//    public void printList() {
//        // To prevent a null pointer exception
//        if(this.head != null) {
//            node<G> temp = this.head;
//            while(temp.getNext() != null) {
//                // Just to makes sure to not get a null pointer exception
//                if(temp.getItem() != null) {
//                    System.out.println(temp.getItem());
//                }
//                temp = temp.getNext();
//            }
//            // To prevent a null pointer exception
//            if(temp.getItem() != null) {
//                System.out.println(temp.getItem());
//            }
//        }
//        else {
//            System.out.println("Empty list.");
//        }
//    }
}

// The node class used by the linked list
class node<G> {
    // Holds what ever is being stored
    private G item;
    // Holds the next item in the list, defaults as null
    private node<G> next = null;

    // Default constructor
    node(G item) {
        this.item = item;
    }

    // Method for setting next
    void setNext(node<G> next) {
        this.next = next;
    }

    // Method for getting what is stored in next
    node<G> getNext() {
        return this.next;
    }

    // Method which returns the value of the object
    G getItem() {
        return this.item;
    }
}