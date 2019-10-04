/*
AvlTree class

CONSTRUCTION: with no initializer

******************PUBLIC OPERATIONS*********************
void insert( x )       --> Insert x
void remove( x )       --> Remove x
boolean contains( x )  --> Return true if x is present
boolean remove( x )    --> Return true if x was present
Comparable findMin( )  --> Return smallest item
Comparable findMax( )  --> Return largest item
boolean isEmpty( )     --> Return true if empty; else false
void makeEmpty( )      --> Remove all items
void printTree( )      --> Print tree in sorted order
******************ERRORS********************************
Throws UnderflowException as appropriate
*/

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 *
 * *****  Also note that many functions are commented out, this because they are never used, they still work though. ***
 *
 * @author Mark Allen Weiss
 */
class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * The tree root.
     */
    private AvlNode<AnyType> root;
    private static final int ALLOWED_IMBALANCE = 1;
    /**
     * Variables to keep track of how many added and taken out and such
     */
    private int size;
    private int runningLength;

    /**
     * keeps track of how many have been added total, never decreases.
     * Complexity: O(1) lol
     * @return the total added ever
     */
    int getRunningLength() {
        return runningLength;
    }

    /**
     * size keeps track of the current size of the tree (number of nodes).
     * Complexity: O(1) lol
     * @return the current size of tree
     */
    int getLength() {
        return size;
    }

    /*
     * Construct the tree. With no default value
     * Complexity: O(1) lol
     */
//    private AVLTree() {
//        root = null;
//    }

    /**
     * Construct the tree. With a value to put in first
     * Complexity: O(1) lol
     */
    AVLTree(AnyType element) {
        insert(element);
    }

    /**
     * Insert into the tree; duplicates are allowed.
     * Complexity: O(1) lol
     * @param element the item to insert.
     */
    void insert(AnyType element) {
        this.root = insert(element, this.root);
        this.size++;
        this.runningLength++;
    }

    /**
     * Internal method to insert into a subtree. Duplicates are allowed.
     * Complexity: O(log(n))
     * @param element the item to insert.
     * @param currNode the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert(AnyType element, AvlNode<AnyType> currNode)
    {
        if(currNode == null) {
            return new AvlNode<>(element, null, null);
        }

        int compareResult = element.compareTo(currNode.element);

        if(compareResult < 0) {
            currNode.left = insert(element, currNode.left);
        }
        else {
            currNode.right = insert(element, currNode.right);
        }

        return balance(currNode);
    }

    /*
     * Remove from the tree. Nothing is done if element is not found.
     * Complexity: O(1) lol
     * @param element the item to remove.
     */
//    private void remove(AnyType element) {
//        this.root = remove(element, this.root);
//        this.size--;
//    }

    /*
     * Internal method to remove from a subtree.
     * Complexity: O(log(n))
     * @param element the item to remove.
     * @param currNode the node that roots the subtree.
     * @return the new root of the subtree.
     */
//    private AvlNode<AnyType> remove(AnyType element, AvlNode<AnyType> currNode) {
//        // Item not found; do nothing
//        if(currNode == null) {
//            return null;
//        }
//
//        int compareResult = element.compareTo(currNode.element);
//
//        if(compareResult < 0) {
//            currNode.left = remove(element, currNode.left);
//        }
//        else {
//            if(compareResult > 0) {
//                currNode.right = remove(element, currNode.right);
//            }
//            // Ready to delete, two children
//            else {
//                if(currNode.left != null && currNode.right != null) {
//                    currNode.element = findMin(currNode.right).element;
//                    currNode.right = remove(currNode.element, currNode.right);
//                }
//                else {
//                    currNode = (currNode.left != null) ? currNode.left : currNode.right;
//                }
//            }
//        }
//        return balance(currNode);
//    }

    /**
     * Find the smallest item in the tree.
     * Complexity: O(1) lol
     * @return smallest item or null if empty.
     */
    AnyType findMin() {
        if(isEmpty()) {
            throw new RuntimeException();
        }
        return findMin(root).element;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * Complexity: O(log(n)) lol
     * @param currNode the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin(AvlNode<AnyType> currNode) {
        if(currNode == null) {
            return null;
        }

        while(currNode.left != null) {
            currNode = currNode.left;
        }
        return currNode;
    }

    /**
     * This will quickly delete the left most node (the smallest).
     * Complexity: O(1) lol
     */
    void deleteMin(){
        root =  deleteMin(root);
        this.size--;
    }

    /**
     * This will delete the left most node (the smallest).
     * Complexity: O(log(n)) lol
     * @param currNode the node, starts as root
     * @return the new root if balancing occurs
     */
    private AvlNode<AnyType> deleteMin(AvlNode<AnyType> currNode) {
        if(currNode == null) {
            return balance(null);
        }
        if(currNode.left == null) {
            return balance(currNode.right);
        }
        currNode.left = deleteMin(currNode.left);
        return balance(currNode);
    }

    /*
     * Find the largest item in the tree.
     * Complexity: O(1) lol
     * @return the largest item of null if empty.
     */
//    public AnyType findMax() {
//        if(isEmpty()) {
//            throw new RuntimeException();
//        }
//        return findMax(root).element;
//    }

    /*
     * Internal method to find the largest item in a subtree.
     * Complexity: O(log(n))
     * @param currNode the node that roots the tree.
     * @return node containing the largest item.
     */
//    private AvlNode<AnyType> findMax(AvlNode<AnyType> currNode) {
//        if(currNode == null) {
//            return null;
//        }
//
//        while(currNode.right != null) {
//            currNode = currNode.right;
//        }
//        return currNode;
//    }

    /*
     * Find an item in the tree.
     * Complexity: O(1) lol
     * @param element the item to search for.
     * @return true if x is found.
     */
//    public boolean contains(AnyType element) {
//        return contains(element, root);
//    }

    /*
     * Internal method to find an item in a subtree.
     * Complexity: O(log(n))
     * @param element is item to search for.
     * @param currNode the node that roots the tree.
     * @return true if x is found in subtree.
     */
//    private boolean contains(AnyType element, AvlNode<AnyType> currNode) {
//        while(currNode != null) {
//            int compareResult = element.compareTo(currNode.element);
//
//            if(compareResult < 0) {
//                currNode = currNode.left;
//            }
//            else {
//                if(compareResult > 0) {
//                    currNode = currNode.right;
//                }
//                // Match
//                else {
//                    return true;
//                }
//            }
//        }
//        // No match
//        return false;
//    }

    /*
     * Make the tree logically empty.
     * Complexity: O(1) lol
     */
//    public void makeEmpty() {
//        root = null;
//    }

    /**
     * Test if the tree is logically empty.
     * Complexity: O(1) lol
     * @return true if empty, false otherwise.
     */
    private boolean isEmpty() {
        return root == null;
    }

    /*
     * Print the tree contents in sorted order.
     * Complexity: O(1) lol
     */
//    private void printTree(String label) {
//        System.out.println(label);
//        if(isEmpty())
//            System.out.println("Empty tree");
//        else
//            printTree(root,"");
//    }

    /*
     * Internal method to print a subtree in sorted order.
     * Complexity: O(n)
     * @param currNode the node that roots the tree.
     * @param indent the amount of space indented for each node based on level
     */
//    private void printTree(AvlNode<AnyType> currNode, String indent) {
//        if(currNode != null) {
//            printTree(currNode.right, indent+"   ");
//            System.out.println(indent + currNode.element + "(" + currNode.height + ")");
//            printTree(currNode.left, indent+"   ");
//        }
//    }

    /*
     * Checks the balance of the tree.
     * Complexity: O(1) lol
     */
//    public void checkBalance() {
//        checkBalance(root);
//    }

    /*
     * function which checks if the tree is balanced.
     * Complexity: O(n)
     * @param currNode the node which you currently at, starts as root
     * @return gives back the height of the node I suppose
     */
//    private int checkBalance(AvlNode<AnyType> currNode) {
//        if(currNode == null) {
//            return -1;
//        }
//        int hl = checkBalance(currNode.left);
//        int hr = checkBalance(currNode.right);
//        if(Math.abs(height(currNode.left) - height(currNode.right)) > 1 ||
//                height(currNode.left) != hl || height(currNode.right) != hr )
//            System.out.println( "\n\n***********************OOPS!!" );
//        return height(currNode);
//    }

    /**
     * Assume currNode is either balanced or within one of being balanced.
     * This is what makes the tree AVL, will adjust nodes to keep a balance within ALLOWED_IMBALANCE.
     * Complexity: O(1)
     * @param currNode the node which start at, should start at root and go down when anything happens (balances up).
     * @return the new node if a balance occurs, so the root can change
     */
    private AvlNode<AnyType> balance(AvlNode<AnyType> currNode) {
        if(currNode == null) {
            return null;
        }
        if(height(currNode.left) - height(currNode.right) > ALLOWED_IMBALANCE) {
            if(height(currNode.left.left) >= height(currNode.left.right)) {
                currNode = rightRotation(currNode);
            }
            else {
                currNode = doubleRightRotation(currNode);
            }
        }
        else {
            if(height(currNode.right) - height(currNode.left) > ALLOWED_IMBALANCE) {
                if(height(currNode.right.right) >= height(currNode.right.left)) {
                    currNode = leftRotation(currNode);
                }
                else {
                    currNode = doubleLeftRotation(currNode);
                }
            }
        }

        currNode.height = Math.max(height(currNode.left), height(currNode.right)) + 1;
        return currNode;
    }

    /**
     * takes a node and returns its height, handles null.
     * Complexity: O(1) lol
     * @param currNode The node that is given.
     * @return the height of the node (max height of its children).
     */
    private int height(AvlNode<AnyType> currNode) {
        if (currNode == null) {
        return -1;
        }
        return currNode.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     * Complexity: O(1)
     * @param currNode the node that you start with (the root of the tree/subtree).
     * @return returns the new root of the subtree after the rotation.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> currNode) {
        AvlNode<AnyType> newRoot = currNode.left;
        currNode.left = newRoot.right;
        newRoot.right = currNode;
        currNode.height = Math.max(height(currNode.left), height(currNode.right)) + 1;
        newRoot.height = Math.max(height(newRoot.left), currNode.height) + 1;
        return newRoot;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     * Complexity: O(1)
     * @param currNode the current node (usually the root).
     * @return the new root of the tree after the rotation.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> currNode) {
        AvlNode<AnyType> newRoot = currNode.right;
        currNode.right = newRoot.left;
        newRoot.left = currNode;
        currNode.height = Math.max(height(currNode.left), height(currNode.right)) + 1;
        newRoot.height = Math.max(height(newRoot.right), currNode.height) + 1;
        return newRoot;
    }

    /**
     * Double rotate binary tree node: first left child.
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     * Complexity: O(1)
     * @param currNode the current node/root of the tree
     * @return the new root of the tree, after the double rotation
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> currNode) {
        currNode.left = leftRotation(currNode.left);
        return rightRotation(currNode);
    }

    /**
     * Double rotate binary tree node: first right child.
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     * Complexity: O(1)
     * @param currNode the current node/root of the tree.
     * @return the new root of the tree after the double rotation.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> currNode) {
        currNode.right = rightRotation(currNode.right);
        return leftRotation(currNode);
    }

    private static class AvlNode<AnyType> {
        // The data in the node
        AnyType           element;
        // Left child
        AvlNode<AnyType>  left;
        // Right child
        AvlNode<AnyType>  right;
        // Height
        int               height;

        // Constructors
//        AvlNode(AnyType theElement) {
//            this(theElement, null, null);
//        }

        AvlNode(AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
            this.element  = theElement;
            this.left     = lt;
            this.right    = rt;
            this.height   = 0;
        }
    }


    // Test program
//    public static void oldMain( String [ ] args ) {
//        AVLTree<Integer> t = new AVLTree<>();
//        AVLTree<Dwarf> t2 = new AVLTree<>();
//
//        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
//        for (String aNameList : nameList) t2.insert(new Dwarf(aNameList));
//
//        t2.printTree("The Tree");
//
//        t2.remove(new Dwarf("Bashful"));
//
//        t2.printTree( "The Tree after delete Bashful" );
//        for (int i=0; i < 8; i++) {
//            t2.deleteMin();
//            t2.printTree( "\n\n The Tree after deleteMin" );
//        }
//
//
//        t.insert(1);
//        t.printTree("");
//        t.insert(2);
//        t.printTree("");
//        t.insert(3);
//        t.printTree("");
//        t.insert(1);
//        t.printTree("");
//        t.insert(1);
//        t.printTree("");
//        t.deleteMin();
//        t.printTree("");
//        t.insert(1);
//        t.printTree("");
//        t.insert(1);
//        t.printTree("");
//        t.deleteMin();
//        t.printTree("a");
//        t.remove(1);
//        t.printTree("remove 1");
//
//    }

}

// Class just for demonstrating the that the AVL tree works
//class Dwarf implements Comparable<Dwarf>{
//    private String data;
//    private int which;
//    private static  int ct = 0;
//    public Dwarf(String x){
//        data = x;
//        which= ct++;
//    }
//    @Override
//    public int compareTo(Dwarf b2){
//        return (this.data.compareTo( b2.data));
//    }
//    public String toString(){
//        return  data + which;
//    }
//}


