import java.util.Random;
import java.util.ArrayList;

public class Tree<E extends Comparable<? super E>> {
    private BinaryNode<E> root;
    private BinaryNode<E> curr;
    private String treeName;
    final private String ENDLINE = "\n";

    /**
     * Public method to create an empty tree
     */
    private Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Public method to create a tree from ArrayList of elements
     * @param arr list of items to add to the tree
     */
    private Tree(ArrayList<E> arr, String label) {
        treeName = label;
        root = null;
        for(E item : arr) {
            insert(item);
        }
    }

    /**
     * Public method to create a tree from array  of elements
     * @param arr list of items to add to the tree
     */
    private Tree(E[] arr, String label) {
        treeName = label;
        root = null;
        for(E item : arr) {
            insert(item);
        }
    }

    /**
     * Insert into the tree; duplicates are allowed.
     * @param x the item to insert.
     */
    private void insert(E x) {
        root = insert(x, root, null);
    }

    /**
     * Internal method to insert into a subtree.
     * Complexity: O(log(n))
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> insert(E x, BinaryNode<E> t, BinaryNode<E> parent) {
        if(t == null)
            return new BinaryNode<>(x, null, null, parent, 0, 0);

        int compareResult = x.compareTo(t.element);

        if(compareResult < 0) {
            t.leftSize++;
            t.left = insert(x, t.left, t);
        }
        else {
            t.rightSize++;
            t.right = insert(x, t.right, t);
        }
        return t;
    }

    // Here are all the functions I have written, inorder of the assignment

    /**
     * Return a string displaying the tree contents as a tree.
     */
    private String prettyTree() {
        if (root == null)
            return (treeName + " Empty tree\n");
        else
            return treeName + ENDLINE + prettyTree(root, 0);
    }

    /**
     * Internal method to print a subtree in sorted order.
     * Complexity: O(n)
     * @param t the node that roots the subtree.
     * @param depth the level where the node is
     */
    private String prettyTree(BinaryNode<E> t, int depth) {
        if(t == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prettyTree(t.right, depth+=1));
        for(int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        sb.append(t.toString());
        sb.append(ENDLINE);
        sb.append(prettyTree(t.left, depth));
        return sb.toString();
    }

    /**
     * Complexity: O(1) LOL
     * Make the tree logically empty.
     */
    private void makeEmpty() {
        root = null;
    }

    /**
     * Public method to count leaf nodes
     * @return number of leaf nodes
     */
    private int countFringe() {
        return countFringe(root);
    }

    /**
     * Complexity: O(n)
     * @param t the root of the tree
     */
    private int countFringe(BinaryNode<E> t) {
        if(t == null) {
            return 0;
        }
        int numLeafLeft = countFringe(t.left);
        int numLeafRight = countFringe(t.right);
        if(numLeafLeft + numLeafRight == 0) {
            return 1;
        }
        return numLeafLeft + numLeafRight;
    }

    /**
     * Public method to find inorder predecessor of the curr node.
     * Uses curr, a local variable set by contains.
     * @return String representation of predecessor
     */
    private String predecessor() {
        if(curr == null) {
            curr = root;
        }
        curr = predecessor(curr);
        if(curr == null) {
            return "null";
        }
        return curr.toString();
    }

    /**
     * Complexity: O(log(n))
     * @param t the root of the tree
     */
    private BinaryNode<E> predecessor(BinaryNode<E> t) {
        if(t == null) {
            return null;
        }
        if(t.left != null) {
            BinaryNode<E> l = t.left;
            while(l.right != null) {
                l = l.right;
            }
            return l;
        }
        if(t.parent == null) {
            return null;
        }
        BinaryNode<E> p = t.parent;
        while(t.element.compareTo(p.element) < 0) {
            if(p.parent == null) {
                return null;
            }
            p = p.parent;
        }
        return p;
    }

    /**
     * Find an item in the tree.
     * @param v the item to search for.
     * @return true if found.
     */
    private boolean contains(E v) {
        return contains(v, root);
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree:
     * a=1, b=2, k=0
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryNode<E> t) {
        curr = null;
        if(t == null)
            return false;
        int compareResult = x.compareTo(t.element);
        if(compareResult < 0){
            return contains(x, t.left);
        }
        else if(compareResult > 0) {
            return contains(x, t.right);
        }
        else {
            curr = t;
            // Match
            return true;
        }
    }

    /**
     * Given a level, returns how many nodes are in that level
     * @param level the depth that you want to see (starts at 0)
     * @return the number of nodes in that level
     */
    private int nodesInLevel(int level) {
        return nodesInLevel(root, level, 0);
    }

    /**
     * Internal method to count number of nodes at level
     * Complexity: O(2^level), because 2^level is the max number of nodes at any given level according to Vicki Allan
     * Complexity: O(n) worst case scenario you have to go through all nodes
     * @param t the node that roots the subtree.
     * @param level, root is level 0
     * @return number of nodes in subtree t at level
     */
    private int nodesInLevel(BinaryNode<E> t, int level, int currLevel) {
        if(t == null) {
            return 0;
        }
        if(currLevel >= level) {
            return 1;
        }
        return nodesInLevel(t.left, level, currLevel + 1) +
                nodesInLevel(t.right, level, currLevel + 1);
    }

    /**
     * goes through a tree and finds the node that is kth in order.
     * @param k, the number that comes kth, smallest number is 1
     * @return a string which come from the node at the kth position in order.
     */
    private String findKthInOrder(int k) {
        if(this.root == null){
            return "NONE";
        }
        BinaryNode<E> kth = findKthInOrder(root, k, root.leftSize + 1);
        if(kth == null) {
            return "NONE";
        }
        else{
            return kth.toString();
        }
    }

    /**
     * Internal method to find the kth value in the tree (by order), smallest in order is 1, 0 is null
     * This routine runs in O(log(n))
     * @param t the node that roots the subtree.
     * @param k, which item is wanted, by order.
     * @param currOrder, the current order of the node that you are at.
     * @return kth successor node
     */
    private BinaryNode<E> findKthInOrder(BinaryNode<E> t, int k, int currOrder) {
        if(t == null) {
            return null;
        }
        if(currOrder > k) {
            if(t.left == null) {
                return null;
            } //                                   This is the math I figured out to get the next current order
            t = findKthInOrder(t.left, k, currOrder - (1 + t.left.rightSize));
        }
        else if(currOrder < k) {
            if(t.right == null) {
                return null;
            } //                                   This is the other math for getting the next current order
            t = findKthInOrder(t.right, k, currOrder + (1 + t.right.leftSize));
        }
        return t;
    }

    /**
     * Helper function which returns the width (in terms of number of nodes) for the tree
     * @return longest distance (num of nodes) between the farthest 2 nodes
     */
    private int width() {
        return width(this.root);
    }

    /**
     * This uses the height variable in each node, DON'T use height for anything else
     * Complexity: O(n)
     * @param t The starting node, the root
     * @return the width of the tree where a leaf is width 1
     */
    private int width(BinaryNode<E> t) {
        if(t == null) {
            return 0;
        }
        if(t.right == null && t.left == null) {
            t.height = 1;
            return 1;
        }
        int leftWid = width(t.left);
        int rightWid = width(t.right);
        t.height = Math.max(height(t.left) + 1, height(t.right) + 1);
        int rootWid = height(t.left) + height(t.right) + 1;
        return Math.max(rootWid, Math.max(leftWid, rightWid));
    }

    /**
     * Helper function which returns the height of a node, can take null
     * @param t the node in question
     * @return the height of that node
     */
    private int height(BinaryNode<E> t) {
        if(t == null) {
            return 0;
        }
        return t.height;
    }

    /**
     * Helper function to see if the tree and a given tree are isomorphic
     * @param t2 the other tree
     * @return whether it is isomorphic or not
     */
    private boolean isIsomorphic(Tree<E> t2) {
        return isIsomorphic(this.root, t2.root);
    }

    /**
     * Internal method to determine if two trees are isomorphic
     * Complexity: O(n)
     * @param t1 one tree
     * @param t2 second tree
     * @return true if t1 and t2 are isomorphic
     */
    private boolean isIsomorphic(BinaryNode<E> t1, BinaryNode<E> t2) {
        if(t1 == null && t2 == null) {
            return true;
        }
        else if(t1 != null && t2 != null) {
            return isIsomorphic(t1.left, t2.left) && isIsomorphic(t1.right, t2.right);
        }
        return false;
    }

    /**
     * Helper function which sees if the tree and a given tree are quasiIsomorphic
     * @param t2 the other tree
     * @return true if tree and t2 are quasiIsomorphic
     */
    private boolean isQuasiIsomorphic(Tree<E> t2) {
        return isQuasiIsomorphic(this.root, t2.root);
    }

    /**
     * Internal method to determine if two trees are quasi- isomorphic
     * Complexity: O(n^2) with a=4, b=2, k=0
     * @param t1 one tree
     * @param t2 second tree
     * @return true if t1 and t2 are quasi isomorphic
     */
    private boolean isQuasiIsomorphic(BinaryNode<E> t1, BinaryNode<E> t2) {
        if(t1 == null && t2 == null) {
            return true;
        }
        else if(t1 != null && t2 != null) {
            return (isQuasiIsomorphic(t1.left, t2.left) && isQuasiIsomorphic(t1.right, t2.right)) ||
                    (isQuasiIsomorphic(t1.left, t2.right) && isQuasiIsomorphic(t1.right, t2.left));
        }
        return false;
    }

    /**
     * Given two numbers in a BST, finds the closest node which connects them
     * complexity: O(log(n))
     * @param a value of one node
     * @param b value of the other node
     * @return the node which is the least common ancestor to those two numbers
     */
    private BinaryNode<E> lca(E a, E b) {
        BinaryNode<E> aNode = findClosest(a);
        BinaryNode<E> bNode = findClosest(b);
        BinaryNode<E> aTemp = aNode;
        aTemp.isAncestor = true;
        // Marks nodes on the way up
        while(aTemp != null) {
            aTemp.isAncestor = true;
            aTemp = aTemp.parent;
        }
        while(bNode != null && !bNode.isAncestor) {
            bNode = bNode.parent;
        }
        // Clears the marks
        while(aNode != null) {
            aNode.isAncestor = false;
            aNode = aNode.parent;
        }
        return bNode;
    }

    /**
     * Find an item in the tree, or if its not, give the node where it would be inserted to (the parent)
     * @param v the item to search for.
     * @return the closest node if not found.
     */
    private BinaryNode<E> findClosest(E v) {
        return findClosest(root, v);
    }

    /**
     * Internal method to find an item or the closest node in a subtree.
     * Complexity: O(log(n))
     * @param v is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private BinaryNode<E> findClosest(BinaryNode<E> t, E v) {
        if(t == null) {
            return null;
        }
        if(t.element.compareTo(v) > 0) {
            if(t.left == null){
                return t;
            }
            return findClosest(t.left, v);
        }
        if(t.element.compareTo(v) < 0) {
            if(t.right == null){
                return t;
            }
            return findClosest(t.right, v);
        }
        return t;
    }

    // Here are all the other functions which where already here



    /**
     * Make a deep copy of the tree.
     */
    private void clone(Tree<E> t) {
        this.root = cloneIt(t.root, null);
    }

    /**
     * Internal method to determine clone the tree
     * This routine runs in O(n)
     * @param t tree
     * @param parent parent node of tree to be created
     * @return cloned tree
     */
    private BinaryNode<E> cloneIt(BinaryNode<E> t, BinaryNode<E> parent) {
        if(t == null) {
            return null;
        }
        BinaryNode<E> n = new BinaryNode<>(t.element, null, null, parent, t.leftSize, t.rightSize);
        n.left = cloneIt(t.left, t);
        n.right = cloneIt(t.right, t);
        return n;
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    private String traverse() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + traverse( root );
    }

    /**
     * Internal method to return a string of items in the tree in order
     * Complexity: O(n)
     * @param t the node that roots the subtree.
     */
    private String traverse(BinaryNode<E> t) {
        if (t == null) return "";
        return traverse(t.left) +
                t.element.toString() +
                " " +
                traverse(t.right);
    }

    /**
     * Returns number of nodes in the tree.
     */
    private int countNodes() {
        return countNodes(root);
    }

    /**
     * Complexity: O(n)
     * @param t the root of the tree
     */
    private int countNodes(BinaryNode<E> t) {
        if(t == null) {
            return 0;
        }
        return countNodes(t.left) + countNodes(t.right) + 1;
    }

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType> {
        // The data in the node
        AnyType element;
        // Left child
        BinaryNode<AnyType> left;
        // Right child
        BinaryNode<AnyType> right;
        // Parent node
        BinaryNode<AnyType> parent;
        int leftSize;
        int rightSize;
        // These have been added to make LCA and width functions easier
        Boolean isAncestor = false;
        // This is only for Width, do not use it for anything else
        int height = 0;

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, BinaryNode<AnyType> pt,
                   int leftSize, int rightSize) {
            this.element = theElement;
            this.left = lt;
            this.right = rt;
            this.leftSize = leftSize;
            this.rightSize = rightSize;
            this.parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element);
            if(parent == null) {
                sb.append( "(No Parent" );
            }
            else{
                sb.append("(");
                sb.append(parent.element);
            }
            sb.append(") [");
            sb.append(leftSize);
            sb.append(",");
            sb.append(rightSize);
            sb.append("]");
            return sb.toString();
        }
    }
    
    // Test program
    public static void main(String[] args) {
        long seed = 436543;
        Random generator = new Random( seed );  // Don't use a seed if you want the numbers to be different each time
        final String ENDLINE = "\n";

        int val = 460;
        final int SIZE = 8;
        Integer[] v0 = {25, 10, 60, 55, 45};
        Integer[] v7 = {30, 15, 65, 75, 83};
        Integer[] v1 = {25, 10, 60, 55, 58, 56, 14, 10, 75, 80, 20, 10, 5, 7, 61, 62, 63};
        ArrayList<Integer> v2 = new ArrayList<>();
        ArrayList<Integer> v3 = new ArrayList<>();
        ArrayList<Integer> v4 = new ArrayList<>();
        ArrayList<Integer> v5 = new ArrayList<>();
        ArrayList<Integer> v6 = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            int t = generator.nextInt( 100 );
            //System.out.println( " t is " + t );
            v2.add( t );
            v3.add( t + generator.nextInt( 5 ) );
            v4.add( t + 18 );
            v5.add( 100 - t );
        }
        for (int i = 0; i < SIZE * SIZE; i++) {
            int t = generator.nextInt( 2000 );
            v6.add( t );
        }
        v6.add( val );
        Tree<Integer> tree0 = new Tree<>( v0, "Tree0:" );
        Tree<Integer> tree1 = new Tree<>( v1, "Tree1:" );
        Tree<Integer> tree2 = new Tree<>( v2, "Tree2:" );
        Tree<Integer> tree3 = new Tree<>( v3, "Tree3:" );
        Tree<Integer> tree4 = new Tree<>( v4, "Tree4:" );
        Tree<Integer> tree5 = new Tree<>( v5, "Tree5:" );
        Tree<Integer> tree6 = new Tree<>( v6, "Tree6:" );
        Tree<Integer> tree7 = new Tree<>( v7, "Tree7:" );
        Tree<Integer> tree8 = new Tree<>( "Tree8:" );

        System.out.println( tree0.prettyTree() );
        System.out.println( tree0.traverse() );
        tree8.clone( tree0 );
        System.out.println( tree8.prettyTree( ) );
        tree8.makeEmpty();
        System.out.println( "Now Empty" + tree8.prettyTree(  ) );
        System.out.println("Not destroyed"+ tree0.prettyTree( ) );

        System.out.println( tree1.prettyTree(  ) );
        System.out.println( "Fringe count=" + tree1.countFringe() );

        System.out.println( tree6.prettyTree(  ) );

        System.out.println( "Size of Tree 6 " + tree6.countNodes() + ENDLINE );
        if(!tree6.contains( val )){ //Sets the current node inside the tree6 class.
            System.out.println("This is just so that I have no warnings...");
        }
        System.out.println( "In Tree6, starting at " + val + ENDLINE );
        System.out.println( tree6.prettyTree( ) );
        int predCount=5;  // how many predecessors do you want to see?
        for (int i = 0; i < predCount; i++) {
            System.out.println( "The next predecessor is " + tree6.predecessor() );
        }
        System.out.println( tree4.prettyTree(  ) );
        System.out.println( "Number nodes at level " + 0 + " is " + tree4.nodesInLevel( 0 ) );
        int myLevel = 3;
        System.out.println( "Number nodes at level " + myLevel + " is " + tree4.nodesInLevel( myLevel ) );
        myLevel = 4;
        System.out.println( "Number nodes at level " + myLevel + " is " + tree4.nodesInLevel( myLevel ) );
        System.out.println( tree1.prettyTree(  ) );
        int k = 1;
        System.out.println( "In tree1, the " + k + "th smallest value is  " + tree1.findKthInOrder( k ) );
        k = 7;
        System.out.println( "In tree1, the " + k + "th smallest value is  " + tree1.findKthInOrder( k ) );
        k = 12;
        System.out.println( "In tree1, the " + k + "th smallest value is  " + tree1.findKthInOrder( k ) );

        System.out.println("The width of  tree1 is " +tree1.width() +ENDLINE);
        System.out.println( tree2.prettyTree( ) );
        System.out.println("The width of  tree2 is " +tree2.width() +ENDLINE);

        System.out.println( tree3.prettyTree(  ) );
        System.out.println( tree4.prettyTree(  ) );
        System.out.println( tree5.prettyTree( ) );

        if (tree2.isIsomorphic( tree3 )) System.out.println( "Trees 2 and 3 are Isomorphic" );
        if (tree2.isIsomorphic( tree4 )) System.out.println( "Trees 2 and 4 are Isomorphic" );
        if (tree3.isIsomorphic( tree4 )) System.out.println( "Trees 3 and 4 are Isomorphic" );
        if (tree0.isIsomorphic( tree1 )) System.out.println( "Trees 2 and 1 Are Isomorphic" );
        if (tree2.isQuasiIsomorphic( tree3 )) System.out.println( "Trees 2 and 3 Are Quasi-Isomorphic" );
        if (tree2.isQuasiIsomorphic( tree5 )) System.out.println( "Trees 2 and 5 Are Quasi-Isomorphic" );
        if (tree2.isQuasiIsomorphic( tree5 )) System.out.println( "Trees 2 and 4 Are Quasi-Isomorphic" );
        if (tree0.isQuasiIsomorphic( tree7 )) System.out.println( "Trees 0 and 7 Are Quasi-Isomorphic" );

        System.out.println( tree1.prettyTree( ) );
        System.out.println( "Least Common Ancestor of (56,61) " + tree1.lca( 56, 61 ) + ENDLINE );
        System.out.println( "Least Common Ancestor (58,55) " + tree1.lca( 58, 55 ) + ENDLINE );

// Other various tests
//        Integer[] t1 = {10, 5, 15, 1, 7, 12, 20};
//        Tree<Integer> T1 = new Tree<>(t1, "t1");
//        System.out.println(T1.prettyTree());
//
//        System.out.println("**********************************************************");
//
//        Integer[] t2 = {81, 30, 90, 18, 44, 23, 35, 31};
//        Tree<Integer> T2 = new Tree<>(t2, "t2");
//        System.out.println(T2.prettyTree());
//
//        System.out.println("**********************************************************");
//
//        Tree<Integer> T3 = new Tree<>("t3");
//        T3.clone(T1);
//        System.out.println(T3.prettyTree());
//        System.out.println(T1.prettyTree());
//        T3.makeEmpty();
//        System.out.println(T3.prettyTree());
//        System.out.println(T1.prettyTree());
//
//        System.out.println("**********************************************************");
//
//        System.out.println(T1.countFringe());
//        System.out.println(T2.countFringe());
//        System.out.println(T3.countFringe());
//
//        System.out.println("**********************************************************");
//
//        Integer[] t4 = {11, 9, 15, 6, 14, 18, 4, 7, 21};
//        Tree<Integer> T4 = new Tree<>(t4, "t4");
//        T4.contains(14);
//        System.out.println(T4.predecessor());
//        T4.contains(15);
//        System.out.println(T4.predecessor());
//        T4.contains(7);
//        System.out.println(T4.predecessor());
//
//        System.out.println("**********************************************************");
//
//        System.out.println(T1.nodesInLevel(0));
//        System.out.println(T1.nodesInLevel(1));
//        System.out.println(T1.nodesInLevel(2));
//        System.out.println(T1.nodesInLevel(3) + "\n");
//
//        System.out.println(T2.nodesInLevel(0));
//        System.out.println(T2.nodesInLevel(1));
//        System.out.println(T2.nodesInLevel(2));
//        System.out.println(T2.nodesInLevel(3));
//        System.out.println(T2.nodesInLevel(4) + "\n");
//
//        System.out.println(T3.nodesInLevel(0));
//        System.out.println(T3.nodesInLevel(1));
//        System.out.println(T3.nodesInLevel(2));
//        System.out.println(T3.nodesInLevel(3));
//        System.out.println(T3.nodesInLevel(4) + "\n");
//
//        System.out.println(T4.nodesInLevel(0));
//        System.out.println(T4.nodesInLevel(1));
//        System.out.println(T4.nodesInLevel(2));
//        System.out.println(T4.nodesInLevel(3));
//        System.out.println(T4.nodesInLevel(4) + "\n");
//
//        System.out.println("**********************************************************");
//
//        System.out.println(T1.prettyTree());
//        System.out.println(T1.findKthInOrder(-1));
//        System.out.println(T1.findKthInOrder(0));
//        System.out.println(T1.findKthInOrder(1));
//        System.out.println(T1.findKthInOrder(2));
//        System.out.println(T1.findKthInOrder(3));
//        System.out.println(T1.findKthInOrder(4));
//        System.out.println(T1.findKthInOrder(5));
//        System.out.println(T1.findKthInOrder(6));
//        System.out.println(T1.findKthInOrder(7));
//
//        System.out.println(T4.prettyTree());
//        System.out.println(T4.findKthInOrder(-1));
//        System.out.println(T4.findKthInOrder(0));
//        System.out.println(T4.findKthInOrder(1));
//        System.out.println(T4.findKthInOrder(2));
//        System.out.println(T4.findKthInOrder(3));
//        System.out.println(T4.findKthInOrder(4));
//        System.out.println(T4.findKthInOrder(5));
//        System.out.println(T4.findKthInOrder(6));
//        System.out.println(T4.findKthInOrder(7));
//        System.out.println(T4.findKthInOrder(8));
//        System.out.println(T4.findKthInOrder(9));
//        System.out.println(T4.findKthInOrder(10));
//        System.out.println(T4.findKthInOrder(11));
//
//        System.out.println(T3.prettyTree());
//        System.out.println(T3.findKthInOrder(-1));
//        System.out.println(T3.findKthInOrder(0));
//        System.out.println(T3.findKthInOrder(1));
//        System.out.println(T3.findKthInOrder(2));
//        System.out.println(T3.findKthInOrder(3));
//        System.out.println(T3.findKthInOrder(4));
//        System.out.println(T3.findKthInOrder(5));
//        System.out.println(T3.findKthInOrder(6));
//        System.out.println(T3.findKthInOrder(7));
//
//
//        System.out.println("**********************************************************");
//
//        System.out.println(T1.width());
//        System.out.println(T2.width());
//        System.out.println(T3.width());
//        System.out.println(T4.width());
//
//        System.out.println("**********************************************************");
//
//        Integer[] t5 = {20, 10, 50, 15, 40, 42};
//        Tree<Integer> T5 = new Tree<>(t5, "t5");
//        Integer[] t6 = {80, 5, 85, 15, 81, 83};
//        Tree<Integer> T6 = new Tree<>(t6, "t6");
//        Tree<Integer> T7 = new Tree<>("t7");
//        T7.clone(T6);
//        T7.insert(8);
//        T7.insert(9);
//        T7.insert(18);
//
//        System.out.println(T5.isIsomorphic(T6));
//        System.out.println(T5.isIsomorphic(T7));
//        System.out.println(T6.isIsomorphic(T5));
//        System.out.println(T6.isIsomorphic(T7));
//        System.out.println(T7.isIsomorphic(T5));
//        System.out.println(T7.isIsomorphic(T6));
//
//        System.out.println("**********************************************************");
//
//        Integer[] t9 = {20, 10, 50, 8, 90, 60};
//        Tree<Integer> T9 = new Tree<>(t9, "t9");
//        Integer[] t8 = {80, 5, 85, 3, 81, 83};
//        Tree<Integer> T8 = new Tree<>(t8, "t8");
//
//        System.out.println(T5.isQuasiIsomorphic(T8));
//        System.out.println(T5.isQuasiIsomorphic(T9));
//        System.out.println(T5.isQuasiIsomorphic(T3));
//        System.out.println(T8.isQuasiIsomorphic(T5));
//        System.out.println(T8.isQuasiIsomorphic(T9));
//        System.out.println(T8.isQuasiIsomorphic(T3));
//        System.out.println(T9.isQuasiIsomorphic(T8));
//        System.out.println(T9.isQuasiIsomorphic(T5));
//        System.out.println(T9.isQuasiIsomorphic(T3));
//        System.out.println(T3.isQuasiIsomorphic(T8));
//        System.out.println(T3.isQuasiIsomorphic(T9));
//        System.out.println(T3.isQuasiIsomorphic(T5));
//
//        System.out.println("**********************************************************");
//
//        Integer[] t10 = {20, 10, 50, 8, 12, 40, 82, 11, 57};
//        Tree<Integer> T10 = new Tree<>(t10, "t10");
//        System.out.println(T10.lca(82,8));
//        System.out.println(T10.lca(42,50));
//        System.out.println(T10.lca(57,40));
//        System.out.println(T10.lca(11101010,102));
    }
}
